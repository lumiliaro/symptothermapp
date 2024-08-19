import { Slider, SliderProps } from "@mantine/core";
import { useController } from "react-hook-form";

export default function SyTemperatureSlider(props: SliderProps) {
    const { field } = useController({ name: "temperature" });

    return (
        <Slider
            {...field}
            defaultValue={36.2}
            min={33}
            max={42}
            label={(value) => `${value.toFixed(2)} °C`}
            step={0.01}
            styles={{ markLabel: { display: "none" } }}
            size="xl"
            labelAlwaysOn
            {...props}
        />
    );
}
