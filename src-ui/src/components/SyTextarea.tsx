import { Textarea, TextareaProps } from "@mantine/core";
import {
    FieldValues,
    useController,
    UseControllerProps,
} from "react-hook-form";

export type SyTextareaProps = UseControllerProps<FieldValues> & TextareaProps;

export default function SyTextarea(props: SyTextareaProps) {
    const { field } = useController(props);

    return (
        <Textarea
            {...field}
            value={field.value || ""}
            color="blue"
            size="md"
            resize="vertical"
            autosize
            maxLength={1000}
            {...props}
        />
    );
}
