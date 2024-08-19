import type { ConfigFile } from "@rtk-query/codegen-openapi";

const config: ConfigFile = {
    schemaFile: "http://localhost:8080/v3/api-docs",
    apiFile: "./src/store/api/emptyApi.ts",
    apiImport: "emptySplitApi",
    outputFile: "./src/store/api/generatedApi.ts",
    exportName: "api",
    hooks: true,
    tag: true,
    useEnumType: true,
};

export default config;
