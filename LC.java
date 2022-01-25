/*

Trabalho realizado por Gabriel Alexandre Araújo Ribeiro nº41235, aluno de LEI na UBI 2020/2021

Unidade Curricular: Lógica Computacional

Curso: Licenciatura em Engenharia Informática

Ano Letivo: 2020/2021

Turno Prático: PL2

Implementação do Algoritmo de Horn em Java 8 (Java(TM) SE Runtime Environment

Símbologia pessoal: TOP = 1 ; BOT = 0; "->" = ">"

O meu algoritmo é constituído por 3 partes, as quais estão explicadas mais detalhadamente no ficheiro README.txt:

-Verificar se é Fórmula de Horn

-Converter em Forma de Horn

-Verificar se a respectiva Forma de Horn é possível(SAT) ou contraditória(UNSAT)


*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 *
 * @author gabri
 */
public class LC {

    public static void main(String[] args) {


    String s= "" ; //String para ler o input
     String s1= "" ; //String que é passada às funções
    ArrayList<String> formulas=new ArrayList<String>();  //ArrayList de Strings para guardar as fórmulas
    ArrayList<String> formas=new ArrayList<String>(); //ArrayList de Strings para guardas as cláusulas em Formas de Horn
    Scanner scan = new Scanner(System.in);
    while(scan.hasNextLine()){
        s=scan.nextLine();
        if (!s.equalsIgnoreCase("")){
            formulas.add(s);}
        else break;
    }
    for(int i=0;i<formulas.size();i++){
        s1=formulas.get(i);
        if(!(VerificaSeFormula(s1))) System.out.println("NA"); //Se fórmula não for fórmula de Horn, output é NA

        else{
          formas=ConverterParaForma(s1);

          if(VerificaSePossivel(formas)){
              System.out.println("SAT");  //Se fórmula de Horn é possível, output é SAT
          }
          else System.out.println("UNSAT"); //Se fórmula de Horn é contraditória, output é UNSAT

        }

    }



    }




public static boolean VerificaSeFormula (String s){  //Função para verificar se é fórmula de Horn
    char c;  //onde vai ser guardado o caracter.
    int count=0; //para efeitos de verificação do número de literais positivos em cada cláusula
    ArrayList<Character> literais=new ArrayList<Character>(); //Arraylist de Chars para guardar os literais positivos
    boolean parenteses = false;  //Boolean para verificar se estamos ainda dentro da mesma Cláusula ou não. Se estamos é True, se não estamos é False
    for(int i=0;i<s.length();i++){
        c=s.charAt(i);
        if(c=='(') parenteses=true;  //Se char for um parêntese '(' significa que entrámos numa cláusula
        if(c==')') parenteses=false; //Se char for um parêntese ')' significa que saímos de uma cláusula
        if(parenteses==true){
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){  //Se char faz parte do alfabeto, ou seja, se é um Literal
                if((!(s.charAt(i-1)=='~')) && !(literais.contains(c)) ){           // Verifica o char anterior, se o char não for um '~' , significa que o literal é positivo. Verifica também se o literal positivo ainda não foi guardado.
                    literais.add(c);  //Se sim, adiciona ao array list o literal positivo
                    count++;  //Incrementa o count.
                }
            }
        }
        if(count>1) return false;  //Se em alguma instância da função, count for maior que 1, é porque existem mais do que um literal positivo na fórmula, e portanto a fórmula não pode ser Fórmula de Horn
        if(parenteses==false) {  //Verifica se a variavel "parenteses" é false. Ou seja, se já saímos da cláusula.
            literais.clear(); //Elimina os literais positivos guardados, porque eram referentes à cláusula anterior
            count=0;  // Põe o count a 0 porque já não importa para esta iteração
        }

    }
return true;
}

public static ArrayList<String> ConverterParaForma(String s){
    int tipo;
    char c;
    ArrayList<String> clausulasFormulas = new ArrayList<>(Arrays.asList(s.split("&")));  //Ao usar o método split nas conjunções, divido as cláusulas e guardo-as no arraylist de Strings "clausulasFormulas"
    ArrayList<String> clausulasFormas=new ArrayList<String>();  //ArrayList de Strings para guardar as cláusulas já convertidas.
    for(String a : clausulasFormulas){
        a=a.replaceAll("\\s+","");  //Elimina espaços em branco e newlines para facilitar a conversão
        tipo=VerificaTipoDeClausula(a);
        if(tipo==1){
            a="("+"1"+">"+a.charAt(1)+")";  //Converte a fórmula para TOP -> literal positivo. Considero no meu programa o 1=TOP, o 0=BOT, e o >= ->
            a=a.replace("(", "");  //Remover os parenteses e as conjunções para facilitar próximo passo
            a=a.replace(")", "");
            a=a.replace("&", "");
            clausulasFormas.add(a); //Adiciona cláusula ao ArrayList de Strings das cláusulas. Podem-se ignorar as conjunções entre cláusulas para o próximo passo


        }

        if(tipo==2){
            a=a.replace("~", "");  //Converte a fórmula para (conjunção de literais positivos ) -> BOT. Aqui elimino os "~" e transformo os literais em positivos.
            a=a.replace("|", "&");  //Aqui transformo as disjunções em conjunções
            a= "("+a+">"+"0"+")";  //Aqui junto tudo
            a=a.replace("(", "");  //Remover os parenteses e as conjunções para facilitar próximo passo
            a=a.replace(")", "");
            a=a.replace("&", "");
            clausulasFormas.add(a); //Adiciona clausula ao ArrayList de Strings das cláusulas. Podem-se ignorar as conjunções entre cláusulas para o próximo passo

        }   ;

        if(tipo==3){
            char aux; //Char auxiliar
            String auxS=""; //String auxiliar para guardar o valor do literal positivo
            for(int i=0;i<a.length();i++){
                aux=a.charAt(i);
                if ((aux >= 'a' && aux <= 'z') || (aux >= 'A' && aux <= 'Z')){  //Se char faz parte do alfabeto, ou seja, se é um Literal
                    if(!(a.charAt(i-1)=='~')) {
                        auxS=String.valueOf(aux);
                        break;
                    } //está guardado o char e o index
                }
            }

            a=a.replace(auxS,"");   //Tira o literal positivo da cláusula
            while( a.contains("||"))  a=a.replace("||", "|"); //Muda as possíveis duplas disjunçoes(porque ao removermos o literal positivo isso pode acontecer) para única disjunçao
            while( a.contains("(|")) a=a.replace("(|","(");  // Pode acontecer estar esta situação (|
            while( a.contains("|)")) a=a.replace("|)", ")");  //Pode acontecer estar esta situação |)
            a=a.replace("|","&");  //Substitui as disjunçoes por conjunções
            a=a.replace("~","");  //Transforma os literais negativos que restam em literais positivos
            a="("+a+">"+auxS+")";  //Transforma a cláusula
            a=a.replace("(", "");  //Remover os parenteses e as conjunções para facilitar próximo passo
            a=a.replace(")", "");
            a=a.replace("&", "");
            clausulasFormas.add(a); //Adiciona clausula ao ArrayList de Strings das cláusulas. Podem-se ignorar as conjunções entre cláusulas para o próximo passo


        }   ;

    }

    return clausulasFormas;  //Devolve as cláusulas da *Fórmula de Horn* convertidas em cláusulas da respectiva *Forma de Horn*, num ArrayList, sem as conjunções entre cláusulas
}

public static int VerificaTipoDeClausula(String s){ //Verifica que tipo de cláusula vamos converter para Forma de Horn de acordo com o Lema 6.3 dos PowerPoints da U.C Lógica Computacional
    boolean literalP=false;  //Verificador de literal positivo
    boolean literalN=false;  //Verificador de literal negativo
    char c;

    for(int i=0; i<s.length();i++){
      c=s.charAt(i);
      if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){  //Se char faz parte do alfabeto, ou seja, se é um Literal
          if(!(s.charAt(i-1)=='~')) literalP=true;   //é literal positivo
          else literalN=true;
      }
    }

    if ((literalP) && (!literalN)) return 1;  //Se só existe um literal positivo e nenhum literal negativo na cláusula, retorna 1
    if ((literalP) && (literalN)) return 3;  //Se existe um literal positivo e algum literal negativo na cláusula, retorna 2
    if ((!literalP) && (literalN)) return 2;  //Se só existem literais negativos na cláusula, retorna 3

    return 0;  //Se retornar 0 deu erro
}

public static boolean VerificaSePossivel(ArrayList<String> s){
    int count=0;
    //for(int i=0;i<s.size();i++) System.out.println(s.get(i));
    //boolean check1=true;
    String[] spl; // Array de Strings para dividir os dois lados da cláusula( PRIMEIRO LADO -> SEGUNDO LADO )
    boolean check1=true; //Boolean para verificar se todos os elementos do PRIMEIRO LADO encontram-se no array "algoritmo"
    boolean check2=true;
    ArrayList<Character> algoritmo=new ArrayList<Character>(); // ArrayList que guarda
    int tamanhoAlgoritmo; //Inteiro para verificar tamanho do "algoritmo"
    algoritmo.add('1'); //O Algoritmo começa sempre com TOP, que é representado pelo "1" no meu programa
    while(!(algoritmo.contains('0'))){
        tamanhoAlgoritmo=algoritmo.size();  //Guarda o tamanho do Algoritmo
        for(int i=0; i< s.size();i++){  //Percorrer as cláusulas da forma de Horn
            //System.out.println(s);
            check2=true;
            spl=s.get(i).split(">");  // divide a cláusula em dois LADOS
            for(int j=0;j<spl[0].length();j++){ //Percorre o PRIMEIRO LADO todo
                //System.out.println(spl[0].charAt(j));
                //System.out.println(spl[0]);
                if((algoritmo.contains(spl[0].charAt(j))))  check1=true;
                if(!(algoritmo.contains(spl[0].charAt(j))))  check2=false;


            }

            if((check1==true) && (check2==true) && !(algoritmo.contains(spl[1].charAt(0)))){

                //System.out.println(spl[0]);
                //System.out.println(spl[1]);
                algoritmo.add(spl[1].charAt(0));  //Se o PRIMEIRO LADO foi percorrido todo e tudo se encontra no algoritmo, adiciona-se ao arraylist "algoritmo" o SEGUNDO LADO(caso nao tenha sido ainda adicionado)

            }//System.out.println("Conteúdo de Algoritmo:");
            //for(char b : algoritmo) System.out.println(b);
        }

        if (tamanhoAlgoritmo==algoritmo.size()) break;  //Se o tamanho do "algoritmo" foi inalterado durante o processo, então não é possível adicionar mais, e o while acaba.
    }
    //for(int i=0;i<algoritmo.size();i++) System.out.println(algoritmo.get(i));
    //for(String a : algoritmo) System.out.println(a);
    if (algoritmo.contains('0')) return false; //Se "algoritmo" tem um BOT, retorna false

    else return true; //Se não, retorna true


      }





}
