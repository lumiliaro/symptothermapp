import fs from "fs/promises";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const sourceFile = path.join(
    __dirname,
    "..",
    "src",
    "build",
    "openapi",
    "schema.json"
);
const targetDir = path.join(__dirname, "src", "store", "api");
const targetFile = path.join(targetDir, "schema.json");

async function copySchema() {
    try {
        // Erstellen Sie das Zielverzeichnis, falls es nicht existiert
        await fs.mkdir(targetDir, { recursive: true });

        // Kopieren Sie die Datei
        await fs.copyFile(sourceFile, targetFile);
        console.info("Schema-Datei erfolgreich kopiert.");
    } catch (err) {
        console.error("Fehler beim Kopieren der Schema-Datei:", err);
        process.exit(1);
    }
}

copySchema();
