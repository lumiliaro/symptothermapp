import { NumberInput, NumberInputProps } from "@mantine/core";
import { useController } from "react-hook-form";

export default function SyTemperatureNumberInput(props: NumberInputProps) {
    const { field } = useController({ name: "temperature" });

    return (
        <NumberInput
            {...field}
            size="md"
            label="Temperatur"
            withAsterisk
            min={33}
            max={42}
            suffix=" °C"
            fixedDecimalScale
            decimalSeparator=","
            decimalScale={2}
            step={0.01}
            {...props}
        />
    );
}
