import { Button } from "@mantine/core";

export default function SyCreateSaveButton(props: {
    isSubmitDisabled?: boolean;
    formType: "create" | "edit";
}) {
    return (
        <Button type="submit" size="lg" disabled={props.isSubmitDisabled}>
            {props.formType === "create" ? "Erstellen" : "Speichern"}
        </Button>
    );
}
