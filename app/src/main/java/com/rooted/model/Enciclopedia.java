package com.rooted.model;

import java.util.ArrayList;

public class Enciclopedia {

    ArrayList<EntradasEnciclopedia> entradasEncliclopedia;
    public Enciclopedia(){
        entradasEncliclopedia = new ArrayList<EntradasEnciclopedia>();
    }

   public void addEntrada(EntradasEnciclopedia entrada){
        this.entradasEncliclopedia.add(entrada);
   }

   public void rellenarEnciclopedia(){

       EntradasEnciclopedia Tomate = new EntradasEnciclopedia("Tomate",
               "El tomate (Solanum lycopersicum) es un fruto rico en vitaminas A y C, ampliamente utilizado en ensaladas, salsas y otros platillos.",
               "Requiere riego frecuente pero moderado, manteniendo la humedad constante en el suelo sin encharcar, especialmente durante la floración y la formación de frutos.",
               "Se siembran las semillas en semilleros y, una vez que las plántulas tienen 4-6 hojas, se trasplantan al suelo, con una separación de 30-50 cm entre plantas y 70-100 cm entre filas.",
               "Se cosechan cuando los frutos alcanzan su color característico (rojo, amarillo o según la variedad) y están firmes al tacto.");
       this.addEntrada(Tomate);

       EntradasEnciclopedia Lechuga = new EntradasEnciclopedia("Lechuga",
               "La lechuga (Lactuca sativa) es una hortaliza de hojas frescas y crujientes, rica en agua, fibra y baja en calorías, ideal para ensaladas.",
               "Requiere riego frecuente y ligero, asegurando que el suelo permanezca húmedo pero no saturado, ya que un exceso de agua puede provocar enfermedades.",
               "Se siembran las semillas directamente en el suelo o en semilleros, con una separación de 20-30 cm entre plantas y 30-40 cm entre filas.",
               "Se cosechan cuando las cabezas o las hojas alcanzan el tamaño deseado, antes de que empiecen a espigarse o florecer.");
       this.addEntrada(Lechuga);

       EntradasEnciclopedia Pimientos = new EntradasEnciclopedia("Pimientos",
               "Los pimientos (Capsicum annuum) son frutos ricos en vitaminas C y A, disponibles en diversas variedades y colores, desde dulces hasta picantes.",
               "Requieren riego moderado y constante, evitando periodos prolongados de sequía, ya que esto puede afectar el desarrollo de los frutos.",
               "Se siembran las semillas en semilleros y, cuando las plántulas tienen 4-6 hojas, se trasplantan al suelo, con una separación de 40-50 cm entre plantas y 60-70 cm entre filas.",
               "Se cosechan cuando los frutos alcanzan su tamaño y color característico según la variedad (verde, rojo, amarillo, etc.).");
       this.addEntrada(Pimientos);

       EntradasEnciclopedia Cebolla = new EntradasEnciclopedia("Cebolla",
               "La cebolla (Allium cepa) es un bulbo rico en antioxidantes, vitaminas y minerales, ampliamente utilizado como base en diversas recetas.",
               "Requiere riego moderado, especialmente en las primeras etapas del crecimiento. Es importante reducir el riego cuando los bulbos comienzan a madurar para evitar que se pudran.",
               "Se siembran directamente las semillas o los bulbos pequeños (cebollines) en surcos de 2-3 cm de profundidad, con una separación de 10-15 cm entre plantas y 30-40 cm entre filas.",
               "Se cosechan cuando las hojas comienzan a amarillear y secarse. Los bulbos se extraen y se dejan secar al sol antes de almacenarlos.");
       this.addEntrada(Cebolla);

       EntradasEnciclopedia Lentejas = new EntradasEnciclopedia("Lentejas",
               "Las lentejas (Lens culinaris) son legumbres pequeñas y planas, ricas en proteínas, fibra y minerales, muy populares en guisos y sopas.",
               " Necesitan riego moderado. Aunque son tolerantes a la sequía, requieren humedad regular durante la germinación y el inicio de la floración.",
               "Se siembran directamente las semillas en surcos de 2-3 cm de profundidad, con una distancia de 20-30 cm entre filas.",
               "Se cosechan cuando las vainas están secas y de color marrón. Las plantas se arrancan y se dejan secar al sol antes de desgranar las lentejas.");
       this.addEntrada(Lentejas);

       EntradasEnciclopedia Frijoles = new EntradasEnciclopedia("Frijoles",
               "Los frijoles (Phaseolus vulgaris) son legumbres ricas en proteínas, carbohidratos y minerales, ampliamente utilizadas en diversas cocinas del mundo.",
               "Requieren riego moderado, asegurando que el suelo permanezca húmedo pero no encharcado, especialmente durante la floración y la formación de vainas.",
               "Se siembran directamente las semillas a 2-5 cm de profundidad, con una separación de 10-15 cm entre plantas y 40-60 cm entre filas.",
               "Se cosechan cuando las vainas están llenas y verdes si se desea consumirlas frescas, o secas y quebradizas para consumo de los granos.");
       this.addEntrada(Frijoles);

       EntradasEnciclopedia Garbanzos = new EntradasEnciclopedia("Garbanzos",
               "Los garbanzos (Cicer arietinum) son legumbres redondeadas y ricas en proteínas, fibra y hierro, muy usadas en platos como hummus y cocidos.",
               "Requieren riego ocasional, evitando el exceso de agua. Son tolerantes a la sequía, pero necesitan algo de humedad al inicio del crecimiento y durante la floración.",
               "Se siembran las semillas a 3-6 cm de profundidad, con una distancia de 30-40 cm entre plantas y filas.",
               "Se cosechan cuando las vainas están secas y amarillentas. Las plantas se arrancan y se dejan secar antes de desgranar los garbanzos.");
       this.addEntrada(Garbanzos);

       EntradasEnciclopedia Habas = new EntradasEnciclopedia("Habas",
               "Las habas (Vicia faba) son legumbres grandes y planas, ricas en proteínas, vitaminas y minerales, utilizadas en sopas, guisos y ensaladas.",
               "Requieren riego regular, especialmente durante la germinación y la formación de vainas. Es importante evitar el encharcamiento.",
               "Se siembran las semillas a 5-8 cm de profundidad, con una distancia de 20-30 cm entre plantas y 50-60 cm entre filas.",
               "Se cosechan cuando las vainas están verdes y tiernas si se consumen frescas, o secas y marrones si se quieren los granos secos.");
       this.addEntrada(Habas);

       EntradasEnciclopedia Guisantes = new EntradasEnciclopedia("Guisantes",
               "Los guisantes (Pisum sativum) son legumbres pequeñas y redondas, ricas en proteínas y carbohidratos, utilizadas en una variedad de platillos.",
               "Requieren riego moderado, manteniendo el suelo húmedo pero no encharcado, especialmente durante la floración y la formación de vainas.",
               "Se siembran las semillas a 2-4 cm de profundidad, con una separación de 5-10 cm entre plantas y 40-50 cm entre filas.",
               "Se cosechan cuando las vainas están verdes y llenas para consumo fresco, o cuando están secas para almacenar los granos.");
       this.addEntrada(Guisantes);

       EntradasEnciclopedia Patata = new EntradasEnciclopedia("Patata",
               "La patata (Solanum tuberosum) es un tubérculo rico en carbohidratos, ampliamente cultivado y utilizado en todo el mundo como alimento básico.",
               "Requiere riego moderado, asegurando que el suelo esté húmedo, especialmente durante el desarrollo de los tubérculos. Es importante evitar el encharcamiento para prevenir enfermedades.",
               "Se siembran los tubérculos o trozos de tubérculo con al menos un brote, a una profundidad de 10-15 cm y con una separación de 30-40 cm entre plantas y 60-70 cm entre filas.",
               "Se cosechan cuando las plantas comienzan a secarse y marchitarse. Los tubérculos se extraen cuidadosamente del suelo y se dejan secar antes de almacenarlos.");
       this.addEntrada(Patata);

       EntradasEnciclopedia Yuca = new EntradasEnciclopedia("Yuca",
               "La yuca (Manihot esculenta) es un tubérculo rico en carbohidratos, utilizado en muchas regiones tropicales como fuente principal de energía.",
               "Es tolerante a la sequía, pero requiere riego moderado durante los primeros meses después de la siembra para asegurar un buen desarrollo inicial.",
               "Se siembran trozos de tallo de 20-30 cm de longitud, enterrados de forma inclinada o vertical en el suelo, con una separación de 80-100 cm entre plantas y filas.",
               "Se cosecha entre 8 y 12 meses después de la siembra, cuando las hojas inferiores comienzan a amarillear. Las raíces se extraen cuidadosamente para evitar dañarlas.");
       this.addEntrada(Yuca);

       EntradasEnciclopedia Pepino = new EntradasEnciclopedia("Pepino",
               "El pepino (Cucumis sativus) es una hortaliza fresca y crujiente, rica en agua y baja en calorías, ideal para ensaladas y encurtidos.",
               "Requiere riego abundante y regular, asegurando que el suelo se mantenga húmedo pero sin encharcar, especialmente durante la floración y la formación de los frutos.",
               "Se siembran las semillas directamente en el suelo o en semilleros, a una profundidad de 2-3 cm, con una separación de 30-50 cm entre plantas y 100-150 cm entre filas.",
               "Se cosechan cuando los frutos están tiernos y alcanzan el tamaño deseado, antes de que las semillas en el interior se desarrollen demasiado.");
       this.addEntrada(Pepino);

       EntradasEnciclopedia Calabaza = new EntradasEnciclopedia("Calabaza",
               "La calabaza (Cucurbita spp.) es un fruto versátil, rico en fibra, vitaminas y antioxidantes, utilizado en sopas, purés, postres y guarniciones.",
               "Requiere riego moderado pero constante, manteniendo la humedad del suelo sin encharcar, especialmente durante la formación de los frutos.",
               "Se siembran las semillas directamente en el suelo en pequeños montículos o surcos, a una profundidad de 2-5 cm, con una separación de 1-2 metros entre plantas y filas debido a su crecimiento extensivo.",
               "Se cosechan cuando la cáscara está dura y presenta el color característico de la variedad. Se recomienda dejar secar los frutos al sol durante unos días antes de almacenarlos.");
       this.addEntrada(Calabaza);


   }
}
