import { Group } from "@mantine/core";
import SyCancelButton from "./SyCancelButton";
import SyCreateSaveButton from "./SyCreateSaveButton";

export default function SyFormButtons(props: {
    onReset: () => void;
    isSubmitDisabled?: boolean;
    formType: "create" | "edit";
}) {
    return (
        <Group justify="space-between">
            <SyCancelButton onReset={props.onReset} />
            <SyCreateSaveButton
                isSubmitDisabled={props.isSubmitDisabled}
                formType={props.formType}
            />
        </Group>
    );
}
