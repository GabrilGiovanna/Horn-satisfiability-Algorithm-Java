README

Gabriel Alexandre Araújo Ribeiro

Implementação do Algoritmo de Horn em Java 8 (Java(TM) SE Runtime Environment

Símbologia pessoal: TOP = 1 ; BOT = 0; "->" = ">"

Utilizando a função "VerificaSeFormula", verifico se a fórmula dada através do input, é Fórmula de Horn.
Nesta função, pego na String do input e verifico a existência de literais positivos através do método .charAt(index), enquanto observo os parenteses das cláusulas (Mais à frente torno este processo
mais simples através do uso do método .split(String), no entanto isto envolve mais poder de processamento, e não achei necessário utilizá-lo nesta parte ).

Utilizando as funções "ConverterParaForma" e a função "VerificaTipoDeClausula", converto a fórnula de Horn em Forma de Horn.
Aqui divido as cláusulas na Fórmula de Horn com o do método .split() , guardo-as num ArrayList de Strings, e dependendo do resultado da função "VerificaTipoDeClausula"
(Que recebe uma cláusula de cada vez, na forma de String, contando os literais positivos e negativos de cada cláusula, devolve 1,2 ou 3, de acordo com o Lema 6.3 dos Slides da U.C), converto para o tipo específico de Forma.
É de notar que antes da conversão, utilizo .replaceAll(String regex) para eliminar espaços branco, de forma a facilitar o do algoritmo.
Após isto, e de receber o resultado de "VerificaTipoDeClausula", "monto" uma nova string com a ajuda de .replace(String old,String new), sem parenteses e conjunções(não serão necessários para o próximo passo), e guardo
as cláusulas no ArrayList de Strings "clausulasFormas".

Utilizando a função "VerificaSePossivel", que recebe "clausulasFormas", verifico
se a fórmula de Horn que recebi como input no início, é de facto SAT ou UNSAT.
Começo por guardar no ArrayList de caracteres "algoritmo", o caracter '1'.
Depois entro num while.
Dentro do while, guardo sempre o tamanho do "algoritmo" para comparar com o tamanho final.
Percorro as cláusulas da Forma de Horn, e aí divido as cláusulas, com .split(), em dois lados, o lado antes e depois da "IMP".
Após isso verifico se todos os caracteres do PRIMEIRO LADO(lado antes da IMP), encontram-se no "algoritmo", se sim, guardo no "algoritmo"
o SEGUNDO LADO(lado depois da IMP), se ainda não está guardado.
O while continua até "algoritmo" conter um '0'(BOT), ou tiver percorrido as
cláusulas todas sem adicionar nada ao "algoritmo"(ou seja não há nenhuma "IMP" que se enquadre), e isto verifica-se
com a observação do tamanho do ArrayList "algoritmo", no início e no fim do while.
