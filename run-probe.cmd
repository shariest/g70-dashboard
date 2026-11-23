@echo off
setlocal DisableDelayedExpansion
git -C "%~dp0." -c alias.g70-probe="!bash ./run-probe.sh" g70-probe
exit /b %ERRORLEVEL%
