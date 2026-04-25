package metrics;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
public class OOMetricsSuite {
    private static final String[] PACKAGES = {"main", "metrics", "Measures", "experiment", "fraud", "fraud.algo"};
    private static final int WMC_THRESHOLD = 20, DIT_THRESHOLD = 5, CBO_THRESHOLD = 10;
    private final Path root = Paths.get("").toAbsolutePath().normalize();
    private final List<Class<?>> projectClasses = new ArrayList<>();
    public OOMetricsSuite() {
        Set<String> seen = new HashSet<>();
        for (String pkg : PACKAGES) {
            Path dir = root.resolve(pkg.replace('.', File.separatorChar));
            if (!Files.exists(dir)) continue;
            try (Stream<Path> paths = Files.walk(dir)) {
                paths.filter(p -> Files.isRegularFile(p) && p.toString().endsWith(".java")).forEach(p -> {
                    String name = root.relativize(p.toAbsolutePath()).toString().replace(File.separatorChar, '.').replaceAll("\\.java$", "");
                    if (!seen.add(name) || name.endsWith("module-info") || name.endsWith("package-info")) return;
                    try {
                        Class<?> c = Class.forName(name, false, Thread.currentThread().getContextClassLoader());
                        if (!c.isSynthetic()) projectClasses.add(c);
                    } catch (ClassNotFoundException | LinkageError ignored) {}
                });
            } catch (IOException ignored) {}
        }
        projectClasses.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }
    private String source(Class<?> c) {
        try { return Files.readString(root.resolve(c.getName().replace('.', File.separatorChar) + ".java")); }
        catch (IOException ignored) { return ""; }
    }
    public Map<String, Integer> calculateDIT() {
        Map<String, Integer> out = new LinkedHashMap<>();
        for (Class<?> c : projectClasses) {
            int d = 0; Class<?> p = c.getSuperclass();
            while (p != null && p != Object.class) { d++; p = p.getSuperclass(); }
            out.put(c.getName(), d);
        }
        return out;
    }
    public Map<String, Integer> calculateNOC() {
        Map<String, Integer> out = new LinkedHashMap<>();
        for (Class<?> c : projectClasses) {
            int children = 0;
            for (Class<?> other : projectClasses) if (other != c && other.getSuperclass() == c) children++;
            if (children > 0) out.put(c.getName(), children);
        }
        return out;
    }
    public Map<String, Integer> calculateWMC() {
        Map<String, Integer> out = new LinkedHashMap<>();
        for (Class<?> c : projectClasses) {
            int n = 0;
            for (Method m : c.getDeclaredMethods()) if (!m.isSynthetic()) n++;
            out.put(c.getName(), n);
        }
        return out;
    }
    public Map<String, Integer> calculateCBO() {
        Map<String, Integer> out = new LinkedHashMap<>();
        Set<String> names = new HashSet<>();
        for (Class<?> c : projectClasses) names.add(c.getName());
        for (Class<?> c : projectClasses) {
            Set<String> coupled = new HashSet<>();
            for (Field f : c.getDeclaredFields()) {
                Class<?> t = f.getType(); while (t.isArray()) t = t.getComponentType();
                if (!t.isPrimitive() && !t.getName().startsWith("java.") && names.contains(t.getName())) coupled.add(t.getName());
            }
            for (Method m : c.getDeclaredMethods()) {
                if (m.isSynthetic()) continue;
                Class<?> rt = m.getReturnType(); while (rt.isArray()) rt = rt.getComponentType();
                if (!rt.isPrimitive() && !rt.getName().startsWith("java.") && names.contains(rt.getName())) coupled.add(rt.getName());
                for (Class<?> pt : m.getParameterTypes()) {
                    while (pt.isArray()) pt = pt.getComponentType();
                    if (!pt.isPrimitive() && !pt.getName().startsWith("java.") && names.contains(pt.getName())) coupled.add(pt.getName());
                }
            }
            coupled.remove(c.getName());
            out.put(c.getName(), coupled.size());
        }
        return out;
    }
    public Map<String, Integer> calculateRFC() {
        Map<String, Integer> out = new LinkedHashMap<>(), wmc = calculateWMC();
        // RFC: counts distinct method calls using source pattern matching.
        Pattern call = Pattern.compile("\\b\\w+\\.(\\w+)\\s*\\(");
        for (Class<?> c : projectClasses) {
            Set<String> called = new HashSet<>(); Matcher m = call.matcher(source(c));
            while (m.find()) called.add(m.group(1));
            out.put(c.getName(), wmc.getOrDefault(c.getName(), 0) + called.size());
        }
        return out;
    }
    public Map<String, Integer> calculateLCOM() {
        Map<String, Integer> out = new LinkedHashMap<>();
        Pattern start = Pattern.compile("(?m)^\\s*(public|private|protected)?\\s*(static\\s+)?[\\w<>,\\[\\]\\s]+\\s+\\w+\\s*\\([^)]*\\)\\s*\\{");
        for (Class<?> c : projectClasses) {
            Field[] fields = c.getDeclaredFields();
            Map<String, Integer> fieldAccessCount = new LinkedHashMap<>(); for (Field f : fields) fieldAccessCount.put(f.getName(), 0);
            List<Set<String>> usedByMethod = new ArrayList<>(); String src = source(c); Matcher m = start.matcher(src);
            while (m.find()) {
                // Find the full method block with brace depth to estimate field usage per method.
                int open = src.indexOf('{', m.start()), depth = 0, end = -1;
                for (int i = open; i < src.length(); i++) {
                    char ch = src.charAt(i);
                    if (ch == '{') depth++; else if (ch == '}' && --depth == 0) { end = i; break; }
                }
                if (end < 0) continue;
                String body = src.substring(open + 1, end); Set<String> used = new HashSet<>();
                for (Field f : fields) {
                    String n = f.getName(), cap = n.isEmpty() ? n : Character.toUpperCase(n.charAt(0)) + n.substring(1);
                    if (body.contains("this." + n) || body.matches("(?s).*\\b" + Pattern.quote(n) + "\\b.*") || body.contains("get" + cap + "(")) {
                        used.add(n); fieldAccessCount.put(n, fieldAccessCount.get(n) + 1);
                    }
                }
                usedByMethod.add(used);
            }
            int p = 0, q = 0;
            for (int i = 0; i < usedByMethod.size(); i++) {
                for (int j = i + 1; j < usedByMethod.size(); j++) {
                    boolean shared = false;
                    for (String n : usedByMethod.get(i)) if (usedByMethod.get(j).contains(n)) { shared = true; break; }
                    if (shared) q++; else p++;
                }
            }
            out.put(c.getName(), Math.max(0, p - q));
        }
        return out;
    }
    public void printReport() {
        Map<String, Integer> dit = calculateDIT(), noc = calculateNOC(), wmc = calculateWMC(), cbo = calculateCBO(), rfc = calculateRFC(), lcom = calculateLCOM();
        System.out.println("========== OBJECT-ORIENTED METRICS REPORT ==========");
        System.out.println();
        System.out.printf("%-32s | %4s | %4s | %4s | %4s | %4s | %5s | %5s%n", "Class Name", "WMC", "DIT", "NOC", "CBO", "RFC", "LCOM", "Risk");
        System.out.println("----------------------------------|------|------|------|------|------|-------|-------");
        for (Class<?> c : projectClasses) {
            String n = c.getName();
            int w = wmc.getOrDefault(n, 0), d = dit.getOrDefault(n, 0), no = noc.getOrDefault(n, 0), cb = cbo.getOrDefault(n, 0), rf = rfc.getOrDefault(n, 0), lc = lcom.getOrDefault(n, 0);
            String risk = (d > 5 || w > 20 || cb > 10 || lc > 10) ? "HIGH" : ((d >= 4 || w >= 15 || cb >= 8 || lc >= 5) ? "MEDIUM" : "LOW");
            System.out.printf("%-32s | %4s | %4s | %4d | %4s | %4d | %5d | %5s%n", n, w > WMC_THRESHOLD ? w + "*" : "" + w, d > DIT_THRESHOLD ? d + "*" : "" + d, no, cb > CBO_THRESHOLD ? cb + "*" : "" + cb, rf, lc, risk);
        }
        System.out.println();
        System.out.println("* = exceeds threshold");
        System.out.println();
        System.out.println("THRESHOLDS:");
        System.out.println("- DIT > 5 warning");
        System.out.println("- WMC > 20 warning");
        System.out.println("- CBO > 10 warning");
        System.out.println();
        System.out.println("Total classes analyzed: " + projectClasses.size());
    }
}
