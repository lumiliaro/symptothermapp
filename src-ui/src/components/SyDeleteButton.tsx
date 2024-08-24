import { Button, ButtonProps, Text } from "@mantine/core";
import { modals } from "@mantine/modals";

type SyDeleteButtonProps = ButtonProps & {
    onConfirm: () => void;
};

export default function SyDeleteButton(props: SyDeleteButtonProps) {
    const { onConfirm, ...rest } = props;
    const openDeleteModal = () =>
        modals.openConfirmModal({
            title: "Löschen bestätigen",
            centered: true,
            children: (
                <Text size="sm">
                    Möchten Sie den Datensatz wirklich löschen?
                </Text>
            ),
            labels: {
                confirm: "Mit Löschen fortfahren",
                cancel: "Nicht löschen",
            },
            confirmProps: { color: "red" },
            onConfirm,
        });

    return (
        <Button onClick={openDeleteModal} color="red" {...rest}>
            Löschen
        </Button>
    );
}
