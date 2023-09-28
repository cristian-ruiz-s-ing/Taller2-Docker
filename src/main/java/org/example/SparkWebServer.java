package org.example;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;

import static spark.Spark.*;

public class SparkWebServer{

    public static void main(String... args){
        port(getPort());
        staticFiles.location("/public");


        get("/log", (req, res) -> {
            String msg ="";
            return logMessage(msg);
        });

        get("/taller2",  (req, res) -> {
            return WebPage.getWebPageTaller2();
        });
        get("/home", (req, res) -> {
            return WebPage.getWebPage();
        });
        get("hello", (req,res) -> "Hello Docker!");
        get("/sin/:valor", (req, res) -> {
          double valor = Double.parseDouble(req.params("valor"));
          double result = Math.sin(valor);
          return result;
        });
        get("/cos/:valor", (req, res) -> {
            double valor = Double.parseDouble(req.params("valor"));
            double result = Math.cos(valor);
            return result;
        });
        get("/palindromo/:cadena", (req, res) -> {
            String invertida = new StringBuilder(req.params("cadena")).reverse().toString();
            return req.params("cadena").equals(invertida);
        });
        get("/vector/:p1/:p2", (req, res) -> {
            Double p1 = Double.parseDouble(req.params("p1"));
            Double p2 = Double.parseDouble(req.params("p2"));
            return Math.sqrt(Math.pow(p1, 2)+Math.pow(p2, 2));
        });
    }

    private static String logMessage(String msg) throws IOException {
        return HttpConnection.logMessage(msg);
    }

    private static String getClient() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Taller 2 docker</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Cadena</h1>\n" +
                "<p>Cadena: </p>\n" +
                "<input type=\"text\" id=\"valor\" placeholder=\"Ingrese un valor\">\n" +
                "<button onclick=\"operarCad()\">Submit</button>\n" +
                "<p id=\"result\"></p>\n" +
                "\n" +
                "<script>\n" +
                "        function operarCad() {\n" +
                "            const cadena = document.getElementById(\"valor\").value;\n" +
                "\n" +
                "            fetch(`http://localhost:4567/taller2/${valor}`, {\n" +
                "                method: 'GET',\n" +
                "                mode: 'no-cors'\n" +
                "                })\n" +
                "                .then(response => response.json())\n" +
                "                .then(data => {\n" +
                "                    document.getElementById(\"result\").textContent = `${data}`;\n" +
                "                })\n" +
                "                .catch(error => {\n" +
                "                    console.error('Error:', error);\n" +
                "                });\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}