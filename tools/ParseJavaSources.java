import javax.tools.*;
import com.sun.source.util.JavacTask;
import java.nio.file.*;
import java.util.*;
/** Syntax-only check; deliberately does NOT claim successful type checking or a native build. */
public final class ParseJavaSources {
    public static void main(String[] args)throws Exception{
        var compiler=ToolProvider.getSystemJavaCompiler();var diagnostics=new DiagnosticCollector<JavaFileObject>();
        try(var files=compiler.getStandardFileManager(diagnostics,null,java.nio.charset.StandardCharsets.UTF_8)){
            List<java.io.File> sources=new ArrayList<>();
            try(var walk=Files.walk(Path.of(args[0]))){walk.filter(p->p.toString().endsWith(".java")).forEach(p->sources.add(p.toFile()));}
            var task=(JavacTask)compiler.getTask(null,files,diagnostics,List.of("--release","21","-proc:none"),null,files.getJavaFileObjectsFromFiles(sources));
            task.parse();boolean bad=false;
            for(var d:diagnostics.getDiagnostics())if(d.getKind()==Diagnostic.Kind.ERROR){System.err.println(d);bad=true;}
            if(bad)throw new AssertionError("Java syntax errors");
            System.out.println("JAVA_21_SYNTAX_PASS files="+sources.size()+" (syntax only; no dependency/type checking)");
        }
    }
}
