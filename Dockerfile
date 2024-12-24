# Étape 1 : Utiliser une image Java 8 de base
FROM openjdk:8-jdk-alpine

# Étape 2 : Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3 : Copier le fichier JAR ou les fichiers nécessaires dans le conteneur
COPY path/vers/ton-fichier.jar app.jar

# Étape 4 : Définir la commande d’exécution
ENTRYPOINT ["java", "-jar", "app.jar"]