@ECHO off
CALL .env.bat
CD ../frontend
CALL npm i
START npm run dev
CD ../backend
START mvnw spring-boot:run