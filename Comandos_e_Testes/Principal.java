package br.ufscar.dc.compiladores.lasemantico;

import br.ufscar.dc.compiladores.lasemantico.LASemanticoParser.ProgramaContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Principal {
    
    public static void main(String args[]) throws IOException {
        
        CharStream cs = CharStreams.fromFileName(args[0]);
        
        LASemanticoLexer lexer = new LASemanticoLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LASemanticoParser parser = new LASemanticoParser(tokens);
        ProgramaContext arvore = parser.programa();
        
        //Definição do arquivo de saida da analise
        String path = args[1];
        System.out.println(path);
        File arquivo = new File(path);
        arquivo.getParentFile().mkdirs();
        if(arquivo.createNewFile()){
            System.out.println("arquivo foi criado");
        }else System.out.println("arquivo já exixtia");
        FileWriter arq = new FileWriter(arquivo);
        PrintWriter gravarArq = new PrintWriter(arq);
        
        Gant as = new Gant();
        as.visitPrograma(arvore);
        LASemanticoUtils.errosSemanticos.forEach((s) -> gravarArq.printf(s));
        
        gravarArq.printf("Fim da compilacao");
        
        arq.close();
    }
}
