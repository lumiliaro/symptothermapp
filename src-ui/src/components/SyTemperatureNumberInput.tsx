import { NumberInput, NumberInputProps } from "@mantine/core";
import { useController } from "react-hook-form";
import { TrackDayDto } from "../store/api/generatedApi";

export type SyTemperatureNumberInputProps = NumberInputProps;

export default function SyTemperatureNumberInput(
    props: SyTemperatureNumberInputProps
) {
    const { field, fieldState } = useController<TrackDayDto, "temperature">({
        name: "temperature",
    });

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
            stepHoldDelay={1000}
            stepHoldInterval={75}
            step={0.01}
            value={field.value || undefined}
            {...props}
            error={fieldState.error?.message}
        />
    );
}
