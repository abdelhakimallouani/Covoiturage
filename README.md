# 🚗 Plateforme de Covoiturage

Une API REST permettant de mettre en relation des conducteurs et des passagers afin de partager des trajets, gérer les réservations et optimiser les déplacements entre plusieurs villes.

---

## 📌 1. Contexte du projet

Dans un contexte où les déplacements entre les villes représentent un coût important pour les particuliers, le covoiturage constitue une alternative économique, pratique et écologique aux moyens de transport traditionnels.

Cependant, l'organisation de ces déplacements nécessite une solution permettant aux conducteurs de publier leurs trajets et aux passagers de rechercher des itinéraires adaptés à leurs besoins, de réserver des places et de gérer leurs réservations.

Ce projet consiste à développer une plateforme de covoiturage sous forme d'une API REST, permettant de centraliser la gestion des conducteurs, des passagers, des véhicules, des trajets et des réservations.

## 🎯 2. Problématique

L'organisation du covoiturage présente plusieurs difficultés :

- Manque de centralisation des offres de transport.
- Difficulté à trouver un trajet correspondant à une ville de départ, une destination et une date précises.
- Gestion complexe des trajets comportant plusieurs points d'arrêt.
- Difficulté à calculer automatiquement le prix d'un trajet selon les points de montée et de descente.
- Risque de dépassement du nombre de places disponibles.
- Gestion des annulations et de la disponibilité des places.
- Nécessité de vérifier la disponibilité des conducteurs et des véhicules.

**Problématique principale :**

Comment concevoir une API REST fiable permettant de gérer efficacement les trajets de covoiturage, les itinéraires à plusieurs arrêts et les réservations, tout en garantissant la cohérence des données et le respect des règles métier ?

## 🎯 3. Objectifs du projet

- Faciliter la mise en relation entre conducteurs et passagers.
- Permettre la publication et la recherche de trajets.
- Gérer les itinéraires comportant plusieurs points d'arrêt.
- Automatiser le calcul des prix selon les segments parcourus.
- Garantir la disponibilité des places.
- Gérer les réservations et leurs annulations.
- Assurer la cohérence des données grâce aux transactions et aux contraintes métier.
- Proposer une API structurée, maintenable et évolutive.

## ⚙️ 4. Fonctionnalités principales

### 👨‍✈️ Gestion des conducteurs

- Créer un compte conducteur.
- Consulter la liste des conducteurs.
- Consulter un conducteur par son identifiant.
- Modifier les informations d'un conducteur.
- Rechercher un conducteur par nom ou email.
- Vérifier l'unicité de l'email et du numéro de permis.

### 👤 Gestion des passagers

- Créer un profil passager.
- Consulter la liste des passagers.
- Consulter un passager par son identifiant.
- Rechercher un passager par email.

### 🚘 Gestion des véhicules

- Enregistrer un véhicule.
- Consulter un véhicule par son identifiant.
- Modifier les informations d'un véhicule.
- Supprimer un véhicule.
- Consulter les véhicules d'un conducteur.
- Vérifier l'unicité de l'immatriculation.
- Vérifier la capacité du véhicule.
- Associer chaque véhicule à son propriétaire.

### 🗺️ Gestion des trajets

- Publier un trajet.
- Consulter les détails d'un trajet.
- Rechercher des trajets selon différents critères.
- Consulter les trajets d'un conducteur.
- Gérer plusieurs points d'arrêt par trajet.
- Calculer les prix cumulés des points d'arrêt.
- Vérifier la disponibilité du conducteur et du véhicule.
- Terminer un trajet.
- Annuler un trajet.
- Gérer automatiquement les statuts des trajets.

### 🎫 Gestion des réservations

- Réserver une ou plusieurs places.
- Consulter une réservation.
- Consulter les réservations d'un passager.
- Consulter les réservations d'un trajet.
- Calculer automatiquement le montant de la réservation.
- Vérifier les places restantes.
- Empêcher les réservations en double.
- Annuler une réservation au moins 24 heures avant le départ.
- Mettre automatiquement à jour le statut du trajet selon les places disponibles.

## 🧠 5. Règles métier principales

### Gestion des trajets

- Un conducteur peut posséder plusieurs véhicules.
- Un véhicule appartient à un seul conducteur.
- Un conducteur ne peut publier un trajet qu'avec son propre véhicule.
- Un trajet doit comporter au minimum deux points d'arrêt.
- Le premier point correspond à la ville de départ et le dernier à la ville d'arrivée.
- Les heures de passage doivent être strictement croissantes.
- Les prix cumulés doivent être strictement croissants.
- Un conducteur et un véhicule ne peuvent pas être affectés à deux trajets qui se chevauchent.
- Le nombre de places proposées ne peut pas dépasser la capacité du véhicule.

### Gestion des réservations

- Une réservation concerne un seul passager et un seul trajet.
- Le point de montée doit précéder le point de descente.
- Le nombre de places réservées doit être disponible.
- Un passager ne peut avoir qu'une seule réservation confirmée par trajet.
- Une réservation ne peut être créée que sur un trajet publié.
- L'annulation est autorisée au moins 24 heures avant le départ.
- Les réservations confirmées déterminent le nombre de places occupées.

### Calcul du prix

Le montant d'une réservation est calculé automatiquement selon les prix cumulés des points d'arrêt.

**Prix du segment :**

`Prix du point de descente - Prix du point de montée`

**Montant total :**

`Prix du segment × Nombre de places réservées`

### Statuts des trajets

| Statut | Description |
|---|---|
| PUBLIE | Le trajet est disponible à la réservation. |
| COMPLET | Toutes les places sont réservées. |
| TERMINE | Le trajet est terminé. |
| ANNULE | Le trajet a été annulé. |

### Statuts des réservations

| Statut | Description |
|---|---|
| CONFIRMEE | La réservation est active. |
| ANNULEE | La réservation a été annulée. |

## 🏗️ 6. Architecture du projet

Le projet adopte une architecture en couches, organisée par domaines métier afin de faciliter la maintenance, les tests et l'évolution de l'application.

```text
src/
├── main/
│   ├── java/ma/covoiturage/
│   │   ├── conducteur/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── mapper/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   ├── passager/
│   │   ├── vehicule/
│   │   ├── trajet/
│   │   ├── reservation/
│   │   └── common/
│   │       └── exception/
│   │
│   └── resources/
│       ├── db/migration/
│       └── application.properties/
│
└── test/
    └── java/ma/covoiturage/
        ├── conducteur/
        ├── passager/
        ├── vehicule/
        ├── trajet/
        └── reservation/
```

### Responsabilités des couches

| Couche | Responsabilité |
|---|---|
| Controller | Exposer les endpoints REST et gérer les requêtes HTTP. |
| Service | Définir les contrats des opérations métier. |
| ServiceImpl | Implémenter les règles métier. |
| Repository | Accéder aux données avec Spring Data JPA. |
| Entity | Représenter les entités persistées en base de données. |
| DTO | Contrôler les données entrantes et sortantes. |
| Mapper | Convertir les DTO en entités et inversement. |
| Common | Centraliser les exceptions et les composants partagés. |

## 🛠️ 7. Technologies utilisées

### Backend

- **Java 17** — Langage de programmation.
- **Spring Boot** — Framework principal pour le développement de l'API REST.
- **Spring Web** — Création des endpoints REST.
- **Spring Data JPA** — Gestion de la persistance.
- **Hibernate** — ORM.
- **Jakarta Bean Validation** — Validation des données.
- **MapStruct** — Mapping entre DTO et entités.
- **Lombok** — Réduction du code répétitif.
- **Spring Transaction Management** — Gestion des transactions métier.

### Base de données

- **PostgreSQL** — Système de gestion de base de données relationnelle.
- **Flyway** — Gestion des migrations SQL et de l'évolution du schéma.

### Tests

- **JUnit 5** — Framework de tests unitaires.
- **Mockito** — Simulation des dépendances.
- **AssertJ** — Assertions fluides et lisibles.
- **Postman** — Tests des endpoints REST.

### Documentation et outils

- **Swagger / OpenAPI** — Documentation et exploration de l'API.
- **Git** — Gestion de versions.
- **GitHub** — Hébergement du code source et collaboration.
- **IntelliJ IDEA** — Environnement de développement.
- **Maven** — Gestion des dépendances et du build.
- **Jira** — Organisation et suivi des tâches.

## 🌿 8. Organisation des branches Git

Le projet utilise une stratégie de branches permettant de séparer le développement des fonctionnalités, les tests et la documentation.

| Branche | Responsabilité |
|---|---|
| `main` | Version stable et finale du projet. |
| `dev` | Branche principale de développement. |
| `feature/conducteurs` | Gestion des conducteurs. |
| `feature/passenger` | Gestion des passagers. |
| `feature/vehicle` | Gestion des véhicules. |
| `feature/trajet` | Publication et gestion des trajets. |
| `feature/reservation` | Gestion des réservations. |
| `feature/api-documentation` | Documentation Swagger / OpenAPI. |
| `test/unit-tests` | Tests unitaires. |
| `docs/project-documentation` | README et documentation du projet. |

### Workflow Git

1. Les nouvelles fonctionnalités sont développées dans leurs branches `feature/*`.
2. Les modifications sont intégrées dans `dev` après vérification.
3. Les tests unitaires sont développés dans `test/unit-tests`.
4. La documentation est maintenue dans `docs/project-documentation`.
5. Lorsque le projet est validé, `dev` est fusionnée dans `main`.

## 🚀 9. Installation et exécution

### Prérequis

Avant de lancer le projet, installer :

- JDK 17 ou supérieur.
- Maven.
- PostgreSQL.
- Git.

### 1. Cloner le projet

```bash
git clone <URL_DU_DEPOT_GITHUB>
cd <NOM_DU_PROJET>
```

### 2. Configurer PostgreSQL

Créer une base de données :

```sql
CREATE DATABASE covoiturage;
```

### 3. Configurer les propriétés

Modifier `src/main/resources/application.properties` :

```properties
spring.application.name=covoiturage

spring.datasource.url=jdbc:postgresql://localhost:5432/covoiturage
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

Remplacer `YOUR_PASSWORD` par le mot de passe PostgreSQL local.

### 4. Lancer les migrations

Les migrations Flyway sont exécutées automatiquement au démarrage de l'application si elles sont correctement configurées.

### 5. Démarrer l'application

Avec Maven :

```bash
mvn spring-boot:run
```

Ou compiler le projet :

```bash
mvn clean install
```

## 🧪 10. Exécution des tests

Exécuter tous les tests :

```bash
mvn test
```

Exécuter les tests d'un module :

```bash
mvn test -Dtest=TrajetServiceImplTest
```

Les tests unitaires vérifient les principales règles métier, les cas nominaux et les cas d'erreur en utilisant JUnit 5, Mockito et AssertJ.

Les tests d'intégration des endpoints peuvent être réalisés avec Postman.

## 📚 11. Documentation de l'API

La documentation interactive est accessible via Postman est configuré.

URL habituelle :

```text
https://abdelhakimallouani-7304220.postman.co/workspace/Abdelhakim-Allouani's-Workspace~bb78607f-f69d-4c6a-83e5-df7fbecefe7a/collection/51595625-047fc83c-922a-40e7-a122-7a21c958adb2?action=share&creator=51595625
```

La documentation permet de consulter les endpoints, leurs paramètres, les formats JSON attendus et les réponses HTTP.

## 🔒 12. Gestion des erreurs

L'API utilise un gestionnaire global d'exceptions afin de centraliser les réponses d'erreur.

| Code HTTP | Signification |
|---|---|
| 200 | Requête exécutée avec succès. |
| 201 | Ressource créée avec succès. |
| 204 | Opération exécutée sans contenu à retourner. |
| 400 | Données invalides ou erreur de validation. |
| 403 | Accès interdit. |
| 404 | Ressource introuvable. |
| 409 | Conflit avec une règle métier. |
| 500 | Erreur interne du serveur. |

## 👨‍💻 13. Auteur

**Abdelhakim Allouani**

Projet de développement Backend — Plateforme de Covoiturage.

---

## 📄 Licence

Projet réalisé dans un cadre pédagogique et de développement logiciel.
