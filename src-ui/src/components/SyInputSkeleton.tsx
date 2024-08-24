import { Skeleton } from "@mantine/core";

export default function SyInputSkeleton() {
    return (
        <>
            <Skeleton height={15} radius="sm" width="20%" />
            <Skeleton height={40} radius="sm" />
        </>
    );
}
