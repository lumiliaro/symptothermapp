import react from "@vitejs/plugin-react-swc";
import { defineConfig, loadEnv } from "vite";

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
    const env = loadEnv(mode, process.cwd());

    return {
        plugins: [react()],
        build: {
            outDir:
                env.VITE_MODE === "production"
                    ? "dist"
                    : "../src/main/resources/static",
            rollupOptions: {
                output: {
                    format: "es",
                    globals: {
                        react: "React",
                        "react-dom": "ReactDOM",
                    },
                },
            },
        },
        server: {
            port: 3232,
        },
    };
});
