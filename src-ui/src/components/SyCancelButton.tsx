import { Button } from "@mantine/core";

export default function SyCancelButton(props: { onReset: () => void }) {
    return (
        <Button type="button" color="gray" onClick={props.onReset}>
            Abbrechen
        </Button>
    );
}
