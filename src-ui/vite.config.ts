import react from "@vitejs/plugin-react-swc";
import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    build: {
        outDir: "dist",
        rollupOptions: {
            output: {
                format: "es",
                globals: {
                    react: "React",
                    "react-dom": "ReactDOM",
                },
                manualChunks(id) {
                    if (/envVars.ts/.test(id)) {
                        return "envVars";
                    }
                },
            },
        },
    },
    server: {
        port: 3232,
    },
});
