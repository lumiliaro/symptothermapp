import { NumberInput, NumberInputProps } from "@mantine/core";
import { useEffect } from "react";
import { useController } from "react-hook-form";

export type SyTemperatureNumberInputProps = NumberInputProps;

export default function SyTemperatureNumberInput(
    props: SyTemperatureNumberInputProps
) {
    const { field, fieldState } = useController({ name: "temperature" });

    useEffect(() => {
        if (field.value !== null && (field.value > 42 || field.value < 33)) {
            field.onChange(36.2);
        }
    }, [field]);

    return (
        <NumberInput
            {...field}
            onChange={(value) => {
                field.onChange(value ? value : undefined);
            }}
            size="md"
            label="Temperatur"
            min={33}
            max={42}
            suffix=" °C"
            fixedDecimalScale
            decimalSeparator=","
            decimalScale={2}
            step={0.01}
            value={field.value || ""}
            {...props}
            error={fieldState.error?.message}
        />
    );
}
