import { Button, ButtonProps } from "@mantine/core";

type SyCreateSaveButtonProps = ButtonProps & {
    formType: "create" | "edit";
};

export default function SyCreateSaveButton(props: SyCreateSaveButtonProps) {
    const { formType, ...rest } = props;

    return (
        <Button type="submit" size="lg" {...rest}>
            {formType === "create" ? "Erstellen" : "Speichern"}
        </Button>
    );
}
