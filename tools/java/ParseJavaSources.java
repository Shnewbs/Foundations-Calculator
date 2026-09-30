import java.nio.file.*;import java.util.*;import javax.tools.*;import com.sun.source.util.JavacTask;
public final class ParseJavaSources {public static void main(String[] args)throws Exception {
 if(args.length!=1)throw new IllegalArgumentException("Provide source directory");var compiler=ToolProvider.getSystemJavaCompiler();if(compiler==null)throw new IllegalStateException("JDK 21 required");
 try(var stream=Files.walk(Path.of(args[0]));var manager=compiler.getStandardFileManager(null,null,null)) {
 var files=stream.filter(p->p.toString().endsWith(".java")).sorted().toList();var diagnostics=new DiagnosticCollector<JavaFileObject>();
 var task=(JavacTask)compiler.getTask(null,manager,diagnostics,List.of("--release","21","-proc:none"),null,manager.getJavaFileObjectsFromPaths(files));task.parse();
 for(var diagnostic:diagnostics.getDiagnostics())if(diagnostic.getKind()==Diagnostic.Kind.ERROR){System.err.println(diagnostic);throw new IllegalStateException("Syntax error");}
 System.out.println("JAVA_21_SYNTAX_PASS files="+files.size()+" (syntax only; no dependency/type checking)");}}
}
