#!/bin/sh
# Abre o RPG de Console (Linux / macOS): ./jogar.sh
# Compila o jogo na primeira vez e depois só executa.
cd "$(dirname "$0")" || exit 1

if ! command -v javac > /dev/null 2>&1; then
    echo "O Java (JDK 17 ou superior) não foi encontrado. Instale em https://adoptium.net"
    exit 1
fi

if [ ! -f out/Main.class ]; then
    echo "Compilando o jogo pela primeira vez..."
    javac -encoding UTF-8 -d out -sourcepath src src/Main.java || exit 1
fi

java -cp out Main "$@"
