No logré deployar la aplicación en google cloud engine. 
Es algo que nunca había hecho (trabajar con apps hosteadas en la nube) y no quería demorarme más por ese tema.
Lo estaré investigando por mi parte para tratar de hacerlo andar. 

Respecto a la app, va a tener que ser iniciada localmente. Luego para probarla le tiro requests por medio
de Postman. 
Los Json son como decía el enunciado, ejemplo del post(/mutant): 
{
"dna":["ATGCGA","CATTCC","TTTFGA","GAGAAG","TCATTA","TCCGCG"]
}

Usé una base de datos en memoria (H2) para guardar los ADN verificados y luego poder ir a buscar en la base 
la cantidad de mutantes/humanos que hay.