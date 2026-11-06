@echo off
REM Abre o RPG de Console com dois cliques (Windows).
REM Compila o jogo na primeira vez e depois so executa.

chcp 65001 > nul
cd /d "%~dp0"
title RPG de Console - A Torre do Dragao

where javac > nul 2> nul
if errorlevel 1 (
    echo O Java ^(JDK 17 ou superior^) nao foi encontrado.
    echo Instale o JDK em https://adoptium.net e abra este arquivo de novo.
    echo.
    pause
    exit /b
)

if not exist "out\Main.class" (
    echo Compilando o jogo pela primeira vez...
    javac -encoding UTF-8 -d out -sourcepath src src\Main.java
    if errorlevel 1 (
        echo Erro ao compilar.
        pause
        exit /b
    )
    cls
)

java -Dstdout.encoding=UTF-8 -cp out Main %*

echo.
pause
