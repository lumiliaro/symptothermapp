import { Select, SelectProps } from "@mantine/core";
import {
    FieldValues,
    useController,
    UseControllerProps,
} from "react-hook-form";
import SyInputSkeleton from "./SyInputSkeleton";

export type SyComboboxProps = UseControllerProps<FieldValues> & SelectProps;

export default function SySelect(props: SyComboboxProps) {
    const { field, fieldState } = useController(props);

    if (!props.data) {
        return <SyInputSkeleton />;
    }

    return (
        <Select
            {...props}
            {...field}
            value={field.value}
            placeholder="Bitte auswählen"
            size="md"
            color="blue"
            error={fieldState.error?.message}
        />
    );
}
