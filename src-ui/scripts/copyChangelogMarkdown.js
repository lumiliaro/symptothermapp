import fs from "fs/promises";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const sourceFile = path.join(__dirname, "..", "CHANGELOG.md");
const targetDir = path.join(__dirname, "..", "public");
const targetFile = path.join(targetDir, "CHANGELOG.md");

async function copyChangelog() {
    try {
        await fs.mkdir(targetDir, { recursive: true });
        await fs.copyFile(sourceFile, targetFile);
        console.info("Changelog-Datei erfolgreich kopiert.");
    } catch (err) {
        console.error("Fehler beim Kopieren der Changelog-Datei:", err);
        process.exit(1);
    }
}

copyChangelog();
