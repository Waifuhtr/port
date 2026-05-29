(function() {
    if (window.LudensErrorRegistered) return;
    window.LudensErrorRegistered = true;
    function reportError(message, source, lineno, colno, error) {
        var stack = error && error.stack ? error.stack : 'No stack trace available';
        var payload = {
            message: message || 'Unknown JS Error',
            source: source || 'unknown',
            line: lineno || 0,
            column: colno || 0,
            stackTrace: stack
        };
        if (window.LudensBridge) {
            window.LudensBridge.call("GameError", JSON.stringify(payload));
        }
    }
    window.onerror = function(message, source, lineno, colno, error) {
        reportError(message, source, lineno, colno, error);
        return false;
    };
    window.addEventListener('unhandledrejection', function(event) {
        var reason = event.reason;
        var msg = reason instanceof Error ? reason.message : String(reason);
        var stack = reason instanceof Error && reason.stack ? reason.stack : 'No stack trace available';
        reportError("Unhandled Promise Rejection: " + msg, "", 0, 0, { stack: stack });
    });
})();
