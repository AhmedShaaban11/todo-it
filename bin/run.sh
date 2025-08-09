set -e
source .env.sh

PIDS=()
cleanup() {
  kill "${PIDS[@]}" 2>/dev/null
  exit 130
}
trap cleanup EXIT INT

cd ../frontend
npm i
npm run dev &
PIDS+=($!)

cd ../backend
./mvnw spring-boot:run &
PIDS+=($!)

wait