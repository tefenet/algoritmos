# Algoritmos varios en Ruby

## explicaion general
cada Algoritmo esta resuelto en un solo archivo, dependiendo la naturaleza del problema la solución puede estar orientada a objetos o no.
cada archivo es un ejecutable para linux, puede modificarse el encabezado para otras plataformas.
al ejecutar el archivo se pasa como argumento otro archivo que contiene casos de prueba, la salida es siempre enumerando cada caso. 
por ejemplo
1. output_del_caso1
2. output_del_caso2
etc.

### palindrome
este algoritmo recibe un numero entero y devuelve el palindromo mayor mas próximo.
el script no espera parametro de entrada, el numero se ingresa por consola, no hay archivo de prueba
### window_binary
Hay​ ​N​ ​ventanas​ ​pegadas​ ​en​ ​una​ ​casa,​ ​una​ ​al​ ​lado​ ​de​ ​la​ ​otra.​ ​ ​En​ ​cada​ ​ventana​ ​se​ ​puede​ ​poner cortina​ ​o​ ​no.
​De​ ​todas​ ​las​ ​posibles​ ​combinaciones​ ​¿En​ ​cuántas​ ​hay​ ​3​ ​ventanas​ ​seguidas​ ​con cortina?
la. <br /> Especificación​ ​Input/Output \
i La​ ​input​ ​de​ ​caso​ es ​una​ ​sola​ ​línea​ ​con​ ​el​ ​número​ ​de​ ​ventanas \
ii. La​ ​output​ ​es​ ​una​ ​sola​ ​línea​ ​por​ ​caso​ ​con​ ​la​ ​cantidad​ ​de​ ​combinaciones \
el archivo de prueba se llama windows_test \
### number_translator
Dado un numero de 12 cifras máximo, este algoritmo devuelve un texto que diga el numero en palabras en español.
ejemplo
|   input   |   output  |
| :-----    | :-----    |
|   #case 1  | 1. ciento veinticuatro|
|   124     |   2. mil      |
|   #case 2  |                 |
|   1000    |               |
el archivo de prueba se llama spokenNumber
### biggerStain
Dado un tablero pintado de colores, decir de que color es la mancha mas grande y que tamaño tiene. Los colores esta representados con una letra (cada letra es in color distinto).
una mancha es un grupo de casillas conectadas por los lados (no en diagonal).
el input es una matriz de letras mayusculas.  po ejemplo:
    AAABBBC
    ABBBCCA
    BBCAAAA
el output es del estilo siguiente
    1. ("B", 8)
se espera que el Orden de ejecucion sea aproximadamente lineal.
este es un caso de Connected-Component-Labeling, la solucion está implementada con BFS, por ser el más eficiente.
los archivos de prueba son matriz.txt, matriz2500, damero_5000, y 500_alterna.

### OcurrenceCounter
cuenta cuantas veces se repite un patrón de texto dentro de otro texto por ejemplo, el patrón 'aa' esta presente 4 veces en 'aaabcdoijkjnaaa'
no tiene archivo de prueba.
recibe como parametro el texto base, sobre el cual se buscaré un patrón.
luego se ingresa por interfaz de comandos la cadena de texto a contar.
retorna la cuenta.
