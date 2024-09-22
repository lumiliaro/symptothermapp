import type { ConfigFile } from "@rtk-query/codegen-openapi";

const config: ConfigFile = {
    schemaFile: "./src/store/api/schema.json",
    apiFile: "./src/store/api/emptyApi.ts",
    apiImport: "emptySplitApi",
    outputFile: "./src/store/api/generatedApi.ts",
    exportName: "api",
    hooks: true,
    tag: true,
    useEnumType: true,
};

export default config;
