<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Secure Password Generator</title>
    <!-- Google Fonts for Modern Typography -->
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="app-container">
        <header class="app-header">
            <h1>FortX<span>Gen</span></h1>
            <p>Generate secure, impenetrable passwords instantly.</p>
        </header>

        <main class="main-content">
            <section class="generator-card glassmorphism">
                <form action="${pageContext.request.contextPath}/generate" method="post">
                    
                    <div class="result-box">
                        <input type="text" id="generatedPassword" readonly 
                               value="${not empty generatedPassword ? generatedPassword : 'Click Generate'}" 
                               ${empty generatedPassword ? 'class="placeholder-text"' : ''} />
                    </div>

                    <div class="settings-group">
                        <label for="lengthSlider">Password Length: <span id="lengthValue">16</span></label>
                        <input type="range" id="lengthSlider" name="length" min="8" max="64" value="16" oninput="updateLength(this.value)">
                    </div>

                    <div class="settings-group toggle-group">
                        <label class="toggle">
                            <input type="checkbox" name="uppercase" checked>
                            <span class="slider"></span>
                            <span class="label-text">Include Uppercase Letters</span>
                        </label>
                        <label class="toggle">
                            <input type="checkbox" name="numbers" checked>
                            <span class="slider"></span>
                            <span class="label-text">Include Numbers</span>
                        </label>
                        <label class="toggle">
                            <input type="checkbox" name="symbols" checked>
                            <span class="slider"></span>
                            <span class="label-text">Include Symbols</span>
                        </label>
                    </div>

                    <div class="settings-group">
                        <label for="website">Save Password For (Optional):</label>
                        <input type="text" id="website" name="website" placeholder="e.g. Gmail, Netflix, Bank">
                    </div>

                    <button type="submit" class="btn-generate">Generate Engine</button>
                </form>
            </section>

            <section class="history-card glassmorphism">
                <h2>Saved Passwords</h2>
                <div class="table-container">
                    <c:choose>
                        <c:when test="${not empty history}">
                            <table class="history-table">
                                <thead>
                                    <tr>
                                        <th>Label / Website</th>
                                        <th>Password</th>
                                        <th>Date Created</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="entry" items="${history}">
                                        <tr>
                                            <td><c:out value="${entry.website}" /></td>
                                            <td class="obfuscated-password" onclick="toggleVisibility(this)">
                                                <span class="dots">&#x2022;&#x2022;&#x2022;&#x2022;&#x2022;&#x2022;&#x2022;&#x2022;</span>
                                                <span class="actual" style="display:none;"><c:out value="${entry.password}" /></span>
                                            </td>
                                            <td><c:out value="${entry.createdAt}" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-state">No passwords saved securely yet.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>
        </main>
    </div>

    <script>
        function updateLength(val) {
            document.getElementById('lengthValue').innerText = val;
        }

        function toggleVisibility(cell) {
            const dots = cell.querySelector('.dots');
            const actual = cell.querySelector('.actual');
            if (dots.style.display === 'none') {
                dots.style.display = 'inline';
                actual.style.display = 'none';
            } else {
                dots.style.display = 'none';
                actual.style.display = 'inline';
            }
        }

        // Prevent full page reload and fetch dynamically
        document.querySelector('form').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const btn = document.querySelector('.btn-generate');
            const originalText = btn.innerText;
            btn.innerText = 'Generating...';

            const formData = new FormData(this);
            
            fetch(this.action, {
                method: this.method,
                body: new URLSearchParams(formData) // Convert FormData to URLSearchParams for Servlet compatibility
            })
            .then(res => res.text())
            .then(html => {
                // Parse the returned HTML document
                const doc = new DOMParser().parseFromString(html, 'text/html');
                
                // Replace only the parts of the page that changed
                document.querySelector('.result-box').innerHTML = doc.querySelector('.result-box').innerHTML;
                document.querySelector('.history-card').innerHTML = doc.querySelector('.history-card').innerHTML;
                
                // Reset UI state
                btn.innerText = originalText;
                document.getElementById('website').value = '';
            })
            .catch(err => {
                console.error('Error:', err);
                btn.innerText = originalText;
            });
        });
    </script>
</body>
</html>
