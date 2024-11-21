cd ./backend
call mvn clean install -Dmaven.test.skip=true package
start mvn spring-boot:run
cd ../frontend
call npm i
start npm run dev
