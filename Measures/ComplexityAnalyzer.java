package Measures;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;
public class ComplexityAnalyzer {
        public static void main(String[] args) throws IOException {
            String folderPath = Paths.get("").toAbsolutePath().toString(); // uses current working directory

            System.out.println("=" .repeat(60));
            System.out.println("CYCLOMATIC COMPLEXITY ANALYZER");
            System.out.println("=" .repeat(60));

            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(file -> analyzeFile(file.toFile()));
        }

        private static void analyzeFile(File file) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));

                // Remove comments so we don't count commented code
                content = removeComments(content);

                // Count all decision points
                int decisions = 0;
                decisions += countMatches(content, "\\bif\\s*\\(");
                decisions += countMatches(content, "\\belse\\s+if\\s*\\(");
                decisions += countMatches(content, "\\bfor\\s*\\(");
                decisions += countMatches(content, "\\bwhile\\s*\\(");
                decisions += countMatches(content, "\\bdo\\s*\\{");
                decisions += countMatches(content, "\\bcase\\s+");
                decisions += countMatches(content, "&&");
                decisions += countMatches(content, "\\|\\|");
                decisions += countMatches(content, "\\?");
                decisions += countMatches(content, "\\bcatch\\s*\\(");
                decisions += countMatches(content, "\\bdefault\\s*:");

                int complexity = decisions + 1; // Base path

                // Skip if no logic (like pure data classes)
                if (complexity > 1 || content.contains("class ")) {
                    System.out.printf("%-40s | Decisions: %2d | Complexity: %2d%n",
                            file.getName(), decisions, complexity);
                }

            } catch (Exception e) {
                System.out.println("Error reading: " + file.getName());
            }
        }

        private static int countMatches(String text, String regex) {
            Matcher matcher = Pattern.compile(regex).matcher(text);
            int count = 0;
            while (matcher.find()) count++;
            return count;
        }

        private static String removeComments(String code) {
            // Remove single line comments //
            code = code.replaceAll("//.*", "");
            // Remove multi-line comments /* ... */
            code = code.replaceAll("/\\*.*?\\*/", "");
            return code;
        }
    }


