package Measures;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.regex.*;
public class SoftwareSize {
        static class Metrics {
            String className;
            int totalLines = 0;
            int codeLines = 0;
            int commentLines = 0;
            int blankLines = 0;
            int decisionPoints = 0;
            int methods = 0;

            int getComplexity() {
                return decisionPoints + 1;
            }
        }

        public static void main(String[] args) throws IOException {
            String folderPath = "C:\\Users\\PC\\IdeaProjects\\src"; // Current folder

            System.out.println("=" .repeat(80));
            System.out.println("SOFTWARE METRICS ANALYZER - Complexity + Size");
            System.out.println("=" .repeat(80));
            System.out.println();

            System.out.printf("%-35s %6s %6s %6s %6s %6s %8s%n",
                    "Class", "Total", "Code", "Cmnt", "Blank", "Dec", "Complex");
            System.out.println("-" .repeat(80));

            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(file -> {
                        Metrics m = analyzeFile(file.toFile());
                        if (m.totalLines > 0) {
                            System.out.printf("%-35s %6d %6d %6d %6d %6d %8d%n",
                                    shortenName(file.getFileName().toString()),
                                    m.totalLines, m.codeLines, m.commentLines, m.blankLines,
                                    m.decisionPoints, m.getComplexity());
                        }
                    });

            System.out.println("-" .repeat(80));
        }

        private static Metrics analyzeFile(File file) {
            Metrics m = new Metrics();
            m.className = file.getName();

            try {
                List<String> lines = Files.readAllLines(file.toPath());
                m.totalLines = lines.size();

                boolean inMultiLineComment = false;

                for (String line : lines) {
                    String trimmed = line.trim();

                    // Count blank lines
                    if (trimmed.isEmpty()) {
                        m.blankLines++;
                        continue;
                    }

                    // Check for multi-line comment start/end
                    if (inMultiLineComment) {
                        m.commentLines++;
                        if (trimmed.contains("*/")) {
                            inMultiLineComment = false;
                        }
                        continue;
                    }

                    if (trimmed.startsWith("//")) {
                        m.commentLines++;
                        continue;
                    }

                    if (trimmed.startsWith("/*")) {
                        m.commentLines++;
                        if (!trimmed.contains("*/")) {
                            inMultiLineComment = true;
                        }
                        continue;
                    }

                    // Code line
                    m.codeLines++;

                    // Remove comments from line for decision counting
                    String code = removeInlineComments(line);

                    // Count decision points (only in code lines)
                    m.decisionPoints += countMatches(code, "\\bif\\s*\\(");
                    m.decisionPoints += countMatches(code, "\\belse\\s+if\\s*\\(");
                    m.decisionPoints += countMatches(code, "\\bfor\\s*\\(");
                    m.decisionPoints += countMatches(code, "\\bwhile\\s*\\(");
                    m.decisionPoints += countMatches(code, "\\bdo\\s*\\{");
                    m.decisionPoints += countMatches(code, "\\bcase\\s+");
                    m.decisionPoints += countMatches(code, "&&");
                    m.decisionPoints += countMatches(code, "\\|\\|");
                    m.decisionPoints += countMatches(code, "\\?");
                    m.decisionPoints += countMatches(code, "\\bcatch\\s*\\(");
                    m.decisionPoints += countMatches(code, "\\bdefault\\s*:");

                    // Count methods (lines with method signature)
                    if (code.matches(".*\\s+\\w+\\s*\\(.*\\).*\\{$") &&
                            !code.contains("class ") && !code.contains("interface ")) {
                        m.methods++;
                    }
                }

            } catch (Exception e) {
                System.err.println("Error: " + file.getName());
            }

            return m;
        }

        private static int countMatches(String text, String regex) {
            Matcher matcher = Pattern.compile(regex).matcher(text);
            int count = 0;
            while (matcher.find()) count++;
            return count;
        }

        private static String removeInlineComments(String line) {
            // Remove // comments
            int slashIndex = line.indexOf("//");
            if (slashIndex >= 0) {
                line = line.substring(0, slashIndex);
            }
            // Remove /* */ comments
            line = line.replaceAll("/\\*.*?\\*/", "");
            return line;
        }

        private static String shortenName(String name) {
            if (name.length() > 35) {
                return name.substring(0, 32) + "...";
            }
            return name;
        }

}

