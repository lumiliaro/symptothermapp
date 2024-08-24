import { Button, Group } from "@mantine/core";

export default function SyFormButtons(props: {
    onReset: () => void;
    isSubmitDisabled?: boolean;
    formType: "create" | "edit";
}) {
    // const { formState } = useFormContext();

    return (
        <Group justify="space-between">
            <Button
                type="button"
                size="lg"
                color="gray"
                onClick={props.onReset}
            >
                Abbrechen
            </Button>

            <Button type="submit" size="lg" disabled={props.isSubmitDisabled}>
                {props.formType === "create" ? "Erstellen" : "Speichern"}
            </Button>
        </Group>
    );
}
