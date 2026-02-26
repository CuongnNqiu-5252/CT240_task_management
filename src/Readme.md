# Project Management System - REST API

A comprehensive Spring Boot REST API for managing projects, tasks, users, and team collaboration.

## 📋 Table of Contents
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Key Design Decisions](#key-design-decisions)

## 🛠 Technology Stack

- **Java**: 25
- **Spring Boot**: 3.2.1
- **Spring Data JPA**: Hibernate ORM
- **PostgreSQL**: Database
- **Lombok**: Reduce boilerplate code
- **MapStruct**: DTO mapping
- **Jakarta Validation**: Request validation
- **Maven**: Build tool

## 🏗 Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```
Controller Layer → Service Layer → Repository Layer → Database
      ↕              ↕                  ↕
    DTOs         Business Logic      Entities
```

### Layers:
1. **Controller**: REST endpoints, request/response handling
2. **Service**: Business logic, transaction management
3. **Repository**: Data access layer
4. **Entity**: JPA entities mapped to database tables
5. **DTO**: Data Transfer Objects for API communication
6. **Mapper**: MapStruct mappers for Entity ↔ DTO conversion

## 📊 Database Schema

### Entities and Relationships:

#### User (nguoidung)
- id (UUID, PK)
- username (unique)
- email (unique)
- password
- avatar
- _destroy (soft delete flag)

**Relationships:**
- One-to-Many: Tasks (as creator)
- One-to-Many: Tasks (as assignee)
- One-to-Many: Comments
- One-to-Many: Notifications
- Many-to-Many: Projects (via ProjectMember)

#### Project (congviec)
- id (UUID, PK)
- ten (name)
- mota (description)
- trangthai (status: ACTIVE, ARCHIVED)

**Relationships:**
- One-to-Many: Tasks
- One-to-Many: BoardColumns
- Many-to-Many: Users (via ProjectMember)

#### ProjectMember (nguoidung_duan)
- Composite PK: (dua_id, con_id)
- vaitro (role: OWNER, MANAGER, MEMBER)

#### Task (thongbao)
- id (UUID, PK)
- tieude (title)
- mota (description)
- ngaytao (createdDate - auto)
- ngayhethan (dueDate)
- trangthai (status: TODO, IN_PROGRESS, DONE)

**Relationships:**
- Many-to-One: Project
- Many-to-One: User (creator)
- Many-to-One: User (assignee)
- One-to-Many: Comments
- One-to-Many: Notifications

#### BoardColumn (cotbang)
- id (UUID, PK)
- ten (name)
- Many-to-One: Project

#### Comment (binhluan)
- id (UUID, PK)
- noidung (content)
- ngaytao (createdDate - auto)
- Many-to-One: User
- Many-to-One: Task

#### Notification (cot)
- id (UUID, PK)
- noidung (content)
- ngaytao (createdDate - auto)
- Many-to-One: User
- Many-to-One: Task (optional)

## 📁 Project Structure

```
src/main/java/com/project/management/
├── ProjectManagementApplication.java
├── config/
│   ├── ApplicationInit.java                    # Configuration classes
├── controller/                # REST Controllers
│   ├── UserController.java
│   ├── ProjectController.java
│   ├── ProjectMemberController.java
│   ├── TaskController.java
│   ├── BoardColumnController.java
│   ├── CommentController.java
│   └── NotificationController.java
├── service/                   # Service interfaces
│   ├── UserService.java
│   ├── ProjectService.java
│   ├── ProjectMemberService.java
│   ├── TaskService.java
│   ├── BoardColumnService.java
│   ├── CommentService.java
│   ├── NotificationService.java
│   └── impl/                 # Service implementations
│       ├── UserServiceImpl.java
│       ├── ProjectServiceImpl.java
│       ├── ProjectMemberServiceImpl.java
│       ├── TaskServiceImpl.java
│       ├── BoardColumnServiceImpl.java
│       ├── CommentServiceImpl.java
│       └── NotificationServiceImpl.java
├── repository/               # JPA Repositories
│   ├── UserRepository.java
│   ├── ProjectRepository.java
│   ├── ProjectMemberRepository.java
│   ├── TaskRepository.java
│   ├── BoardColumnRepository.java
│   ├── CommentRepository.java
│   └── NotificationRepository.java
├── entity/                   # JPA Entities
│   ├── User.java
│   ├── Project.java
│   ├── ProjectMember.java
│   ├── ProjectMemberId.java
│   ├── Task.java
│   ├── BoardColumn.java
│   ├── Comment.java
│   └── Notification.java
├── dto/                      # Data Transfer Objects
│   ├── request/
│   │   ├── UserRequestDTO.java
│   │   ├── ProjectRequestDTO.java
│   │   ├── ProjectMemberRequestDTO.java
│   │   ├── TaskRequestDTO.java
│   │   ├── BoardColumnRequestDTO.java
│   │   ├── CommentRequestDTO.java
│   │   └── NotificationRequestDTO.java
│   └── response/
│       ├── UserResponseDTO.java
│       ├── ProjectResponseDTO.java
│       ├── ProjectMemberResponseDTO.java
│       ├── TaskResponseDTO.java
│       ├── BoardColumnResponseDTO.java
│       ├── CommentResponseDTO.java
│       └── NotificationResponseDTO.java
├── mapper/                   # MapStruct Mappers
│   ├── UserMapper.java
│   ├── ProjectMapper.java
│   ├── ProjectMemberMapper.java
│   ├── TaskMapper.java
│   ├── BoardColumnMapper.java
│   ├── CommentMapper.java
│   └── NotificationMapper.java
├── enums/                    # Enumerations
│   ├── ProjectStatus.java
│   ├── TaskStatus.java
│   └── ProjectRole.java
└── exception/                # Exception handling
    ├── ResourceNotFoundException.java
    ├── ErrorResponse.java
    └── GlobalExceptionHandler.java
```

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Maven 3.6 or higher

### Database Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE project_management;
```

2. Update `application.yml` with your PostgreSQL credentials:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/project_management
    username: your_username
    password: your_password
```

### Build and Run

1. Clone the repository
2. Navigate to project directory
3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/api/v1`

## 📡 API Endpoints

### User Endpoints
- `POST /api/v1/users` - Create new user
- `GET /api/v1/users/{id}` - Get user by ID
- `GET /api/v1/users` - Get all users
- `GET /api/v1/users/active` - Get all active users
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user (hard delete)
- `PATCH /api/v1/users/{id}/soft-delete` - Soft delete user

### Project Endpoints
- `POST /api/v1/projects` - Create new project
- `GET /api/v1/projects/{id}` - Get project by ID
- `GET /api/v1/projects` - Get all projects
- `GET /api/v1/projects/status/{status}` - Get projects by status
- `PUT /api/v1/projects/{id}` - Update project
- `DELETE /api/v1/projects/{id}` - Delete project

### Project Member Endpoints
- `POST /api/v1/project-members` - Add member to project
- `GET /api/v1/project-members/project/{projectId}` - Get all members of a project
- `GET /api/v1/project-members/user/{userId}` - Get all projects of a user
- `DELETE /api/v1/project-members/user/{userId}/project/{projectId}` - Remove member from project

### Task Endpoints
- `POST /api/v1/tasks` - Create new task
- `GET /api/v1/tasks/{id}` - Get task by ID
- `GET /api/v1/tasks` - Get all tasks
- `GET /api/v1/tasks/project/{projectId}` - Get tasks by project
- `GET /api/v1/tasks/assignee/{assigneeId}` - Get tasks by assignee
- `GET /api/v1/tasks/status/{status}` - Get tasks by status
- `PUT /api/v1/tasks/{id}` - Update task
- `DELETE /api/v1/tasks/{id}` - Delete task

### Board Column Endpoints
- `POST /api/v1/board-columns` - Create board column
- `GET /api/v1/board-columns/{id}` - Get board column by ID
- `GET /api/v1/board-columns/project/{projectId}` - Get columns by project
- `PUT /api/v1/board-columns/{id}` - Update board column
- `DELETE /api/v1/board-columns/{id}` - Delete board column

### Comment Endpoints
- `POST /api/v1/comments` - Create comment
- `GET /api/v1/comments/{id}` - Get comment by ID
- `GET /api/v1/comments/task/{taskId}` - Get comments by task
- `PUT /api/v1/comments/{id}` - Update comment
- `DELETE /api/v1/comments/{id}` - Delete comment

### Notification Endpoints
- `POST /api/v1/notifications` - Create notification
- `GET /api/v1/notifications/{id}` - Get notification by ID
- `GET /api/v1/notifications/user/{userId}` - Get notifications by user
- `DELETE /api/v1/notifications/{id}` - Delete notification

## 🎯 Key Design Decisions

### 1. **UUID Primary Keys**
- Used UUID instead of auto-increment IDs for better distributed system support
- Prevents ID enumeration attacks
- Easier to merge data from different sources

### 2. **Lazy Fetching Strategy**
- All relationships use `FetchType.LAZY` by default
- Prevents N+1 query problems
- Better performance for large datasets
- Data loaded only when explicitly accessed

### 3. **DTO Pattern**
- Separate Request and Response DTOs
- Prevents over-posting attacks
- API versioning flexibility
- Clear separation between internal entities and external API contracts

### 4. **MapStruct for Mapping**
- Compile-time code generation (faster than reflection-based mappers)
- Type-safe mapping
- Easy to customize mappings
- Better performance than manual mapping

### 5. **Composite Primary Key for ProjectMember**
- Used `@Embeddable` and `@EmbeddedId` for many-to-many relationship
- Prevents duplicate memberships
- Better query performance
- Clear relationship semantics

### 6. **@JsonIgnore on Collections**
- Prevents infinite recursion in JSON serialization
- Reduces response payload size
- Forces explicit data loading through services

### 7. **Global Exception Handling**
- Centralized error handling with `@RestControllerAdvice`
- Consistent error response format
- Validation error details included
- Better debugging experience

### 8. **Service Layer Transaction Management**
- `@Transactional` at service level
- Read-only transactions for queries
- Atomic operations for data modifications
- Better database performance

### 9. **Soft Delete for Users**
- Boolean `deleted` flag instead of hard delete
- Preserves data integrity
- Audit trail maintenance
- Can be restored if needed

### 10. **Builder Pattern**
- Used Lombok's `@Builder` for cleaner object creation
- Immutable object creation
- More readable code
- Flexible object construction

### 11. **Validation Annotations**
- Jakarta Validation at DTO level
- Early validation before business logic
- Consistent validation rules
- Automatic error responses

### 12. **Repository Query Methods**
- Spring Data JPA query derivation
- Custom finder methods
- Type-safe queries
- Reduced boilerplate code

## 📝 Sample Request/Response

### Create User
**Request:**
```json POST /api/v1/users
{
  "username": "john.doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "avatar": "https://example.com/avatar.jpg"
}
```

**Response:**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "username": "john.doe",
  "email": "john.doe@example.com",
  "avatar": "https://example.com/avatar.jpg",
  "deleted": false
}
```

### Create Task
**Request:**
```json POST /api/v1/tasks
{
  "title": "Implement user authentication",
  "description": "Add JWT-based authentication to the API",
  "dueDate": "2024-12-31T23:59:59",
  "status": "TODO",
  "projectId": "456e4567-e89b-12d3-a456-426614174111",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "assigneeId": "789e4567-e89b-12d3-a456-426614174222"
}
```

**Response:**
```json
{
  "id": "abc12345-e89b-12d3-a456-426614174333",
  "title": "Implement user authentication",
  "description": "Add JWT-based authentication to the API",
  "createdDate": "2024-01-15T10:30:00",
  "dueDate": "2024-12-31T23:59:59",
  "status": "TODO",
  "projectId": "456e4567-e89b-12d3-a456-426614174111",
  "projectName": "API Development",
  "assigneeId": "789e4567-e89b-12d3-a456-426614174222",
  "assigneeName": "jane.smith",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "creatorName": "john.doe"
}
```

## 🔧 Configuration

All configuration is in `src/main/resources/application.yml`:

- Database connection settings
- JPA/Hibernate settings
- Server port and context path
- Logging levels

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Author

Senior Java Backend Developer
