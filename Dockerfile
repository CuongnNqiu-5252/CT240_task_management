# Giai đoạn 1: Build file JAR từ mã nguồn
FROM maven:3.9-sapmachine-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build bỏ qua Tests để tránh lỗi kết nối DB khi đang đóng gói
RUN mvn clean package -DskipTests

# Giai đoạn 2: Tạo image chạy ứng dụng (siêu nhẹ)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copy file jar từ giai đoạn build sang
COPY --from=build /app/target/*.jar app.jar
# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]