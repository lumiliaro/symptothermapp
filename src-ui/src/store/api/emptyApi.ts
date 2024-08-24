import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { getEnvVars } from "../../shared/envVars";

const { ENV_VARS: envVariables } = getEnvVars();

// initialize an empty api service that we'll inject endpoints into later as needed
export const emptySplitApi = createApi({
    baseQuery: fetchBaseQuery({
        baseUrl: envVariables.VITE_BACKEND_URL,
    }),
    endpoints: () => ({}),
    refetchOnReconnect: true,
});
