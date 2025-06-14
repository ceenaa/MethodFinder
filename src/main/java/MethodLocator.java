import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

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
        System.out.println("Found method:");
        System.out.println("Method Name: " + method.getName());
        System.out.println("Start Line: " + method.getRange().get().begin.line);
        System.out.println("End Line: " + method.getRange().get().end.line);
        System.out.println("Method Signature: " + method.getDeclarationAsString());
        System.out.println("Method Body:\n" + method);
    } else {
        System.out.println("No method found at line " + lineNumber);
    }
}
