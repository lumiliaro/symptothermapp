import { Select, SelectProps } from "@mantine/core";
import {
    FieldValues,
    useController,
    UseControllerProps,
} from "react-hook-form";

export type SyComboboxProps = UseControllerProps<FieldValues> & SelectProps;

export default function SySelect(props: SyComboboxProps) {
    const { field } = useController(props);

    if (!props.data) {
        return <></>;
    }

    return (
        <Select
            {...props}
            {...field}
            value={field.value || null}
            placeholder="Bitte auswählen"
            size="md"
            color="blue"
        />
    );
}
