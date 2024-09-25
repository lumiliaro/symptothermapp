import { Button, ButtonProps } from "@mantine/core";

type SyCancelButtonProps = ButtonProps & {
    onReset: () => void;
};

export default function SyCancelButton(props: SyCancelButtonProps) {
    return (
        <Button type="button" color="gray" {...props} onClick={props.onReset}>
            Abbrechen
        </Button>
    );
}
