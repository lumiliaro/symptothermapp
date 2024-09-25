import { Text } from "@mantine/core";
import { useGetServerInfoQuery } from "../store/api/generatedApi";

export default function SyFooter() {
    const { data } = useGetServerInfoQuery(undefined, {
        refetchOnFocus: false,
    });

    return (
        <Text size="xs" fs="center">
            Backend: {data?.version} | Frontend:{" "}
            {import.meta.env.VITE_APP_VERSION} | ENV:{" "}
            {import.meta.env.VITE_MODE}
        </Text>
    );
}
