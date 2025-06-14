import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class MethodLocator {
    
    public static void main(String[] args) throws FileNotFoundException {
    if (args.length != 2) {
        System.out.println("Usage: java MethodLocator <source-file> <line-number>");
        return;
    }

    String filePath = args[0];
    int lineNumber = Integer.parseInt(args[1]);

    FileInputStream in = new FileInputStream(filePath);
    CompilationUnit cu = StaticJavaParser.parse(in);

    Optional<MethodDeclaration> result = cu.findAll(MethodDeclaration.class).stream()
            .filter(method -> method.getRange().isPresent()
                    && method.getRange().get().begin.line <= lineNumber
                    && method.getRange().get().end.line >= lineNumber)
            .findFirst();

    if (result.isPresent()) {
        MethodDeclaration method = result.get();
        
        // Create JSON output
        System.out.println("{");
        System.out.println("  \"found\": true,");
        System.out.println("  \"methodName\": \"" + escapeJson(method.getName().asString()) + "\",");
        System.out.println("  \"startLine\": " + method.getRange().get().begin.line + ",");
        System.out.println("  \"endLine\": " + method.getRange().get().end.line + ",");
        System.out.println("  \"methodSignature\": \"" + escapeJson(method.getDeclarationAsString()) + "\",");
        System.out.println("  \"methodBody\": \"" + escapeJson(method.toString()) + "\"");
        System.out.println("}");
    } else {
        System.out.println("{");
        System.out.println("  \"found\": false,");
        System.out.println("  \"message\": \"No method found at line " + lineNumber + "\"");
        System.out.println("}");
    }
}

private static String escapeJson(String str) {
    return str.replace("\\", "\\\\")
              .replace("\"", "\\\"")
              .replace("\n", "\\n")
              .replace("\r", "\\r")
              .replace("\t", "\\t");
}
}
