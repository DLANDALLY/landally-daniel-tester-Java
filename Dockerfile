# --- Étape 1 : Build (Maven + OpenJDK 8) ---
FROM maven:3.8.8-openjdk-8-slim AS builder
WORKDIR /app
  
# Optimisation du cache des dépendances
COPY pom.xml .
RUN mvn dependency:go-offline -B
  
# Compilation et packaging
COPY src ./src
RUN mvn clean package -DskipTests -B
  
# --- Étape 2 : Runtime sécurisé (JRE 8 Alpine) ---
FROM openjdk:8-jre-alpine
WORKDIR /app
  
# Sécurisation : Création d'un utilisateur non-root
RUN addgroup -S devopsgroup && adduser -S devopsuser -G devopsgroup
USER devopsuser:devopsgroup
  
# Récupération du livrable de l'étape précédente
COPY --from=builder /app/target/*.jar app.jar

# Bonnes pratiques JVM pour Java 8 en conteneur (gestion stricte de la mémoire)
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=2 -XshowSettings:vm"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]