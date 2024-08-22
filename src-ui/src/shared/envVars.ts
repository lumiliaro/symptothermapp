type EnvVarsType = Pick<ImportMetaEnv, "VITE_BACKEND_URL">;

// Environment Variable Template to Be Replaced at Runtime
const envVars: EnvVarsType = {
    VITE_BACKEND_URL: "${VITE_BACKEND_URL}",
};

// Returning the variable value from runtime or obtained as a result of the build
export const getEnvVars = (): {
    ENV_VARS: EnvVarsType;
} => {
    return {
        ENV_VARS: {
            VITE_BACKEND_URL: !envVars.VITE_BACKEND_URL.includes("VITE_")
                ? envVars.VITE_BACKEND_URL
                : import.meta.env.VITE_BACKEND_URL,
        },
    };
};
