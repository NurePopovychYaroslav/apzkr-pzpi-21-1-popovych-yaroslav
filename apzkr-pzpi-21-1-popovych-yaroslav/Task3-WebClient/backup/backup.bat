@echo off

REM Set environment variables
set CONTAINER_NAME=e-lock-backend
set DB_NAME=e-lock-backend
set DB_USER=postgres
set DB_PASSWORD=admin
set BACKUP_DIR=%~dp0backups
set BACKUP_FILE=%BACKUP_DIR%\%DB_NAME%_backup_%DATE:~10,4%-%DATE:~4,2%-%DATE:~7,2%.sql

REM Ensure the backup directory has write permissions
if not exist %BACKUP_DIR% (
    mkdir %BACKUP_DIR%
    if %errorlevel% neq 0 (
        echo Failed to create backup directory: %BACKUP_DIR%
        pause
        exit /b 1
    )
)

REM Check if the backup file already exists and handle it
if exist %BACKUP_FILE% (
    echo Backup file already exists. Removing the old backup file.
    del %BACKUP_FILE%
    if %errorlevel% neq 0 (
        echo Failed to remove existing backup file: %BACKUP_FILE%
        pause
        exit /b 1
    )
)

REM Run the backup command
docker exec -e PGPASSWORD=%DB_PASSWORD% %CONTAINER_NAME% pg_dump -U %DB_USER% %DB_NAME% -F c > %BACKUP_FILE%
if %errorlevel% neq 0 (
    echo Backup command failed
    pause
    exit /b 1
)

REM Check if the backup file was created successfully
if exist %BACKUP_FILE% (
    echo Backup completed successfully: %BACKUP_FILE%
) else (
    echo Backup failed
    pause
    exit /b 1
)

pause
