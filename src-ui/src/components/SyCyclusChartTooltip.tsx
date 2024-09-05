import { Paper, Text } from "@mantine/core";
import dayjs from "dayjs";
import { Payload } from "recharts/types/component/DefaultTooltipContent";
import { DATETIME_FORMAT_UI } from "../utils/DateFormats.utils";

export default function SyCyclusChartTooltip(props: {
    payload?: Payload<any, any>[];
    label?: string;
}) {
    const { payload, label } = props;
    return (
        <Paper px="md" py="sm" withBorder shadow="md" radius="md">
            <Text fw={500} mb={5}>
                Tag: {label}
            </Text>
            {payload?.map((item) => (
                <>
                    <Text key={`${item.payload.date}-cyclusDay`} fz="sm">
                        Zyklustag: {item.payload.cyclusDay}
                    </Text>
                    <Text key={`${item.payload.date}-temperature`} fz="sm">
                        Temperatur: {item.payload.temperature} {" °C"}
                    </Text>
                    {item.payload.cervicalMucus && (
                        <Text
                            key={`${item.payload.date}-cervicalMucus`}
                            fz="sm"
                        >
                            Zervixschleim: {item.payload.cervicalMucus}
                        </Text>
                    )}
                    {item.payload.bleeding && (
                        <Text
                            key={`${item.payload.date}-bleeding`}
                            c="red.6"
                            fz="sm"
                        >
                            Blutung: {item.payload.bleeding}
                        </Text>
                    )}

                    <Text key={`${item.payload.date}-createdAt`} fz="sm">
                        {"Messung am: "}
                        {dayjs(item.payload.createdAt).format(
                            DATETIME_FORMAT_UI
                        )}
                        {" Uhr"}
                    </Text>
                    <Text key={`${item.payload.date}-updatedAt`} fz="sm">
                        {"Aktualisiert am: "}
                        {dayjs(item.payload.updatedAt).format(
                            DATETIME_FORMAT_UI
                        )}
                        {" Uhr"}
                    </Text>
                </>
            ))}
        </Paper>
    );
}
