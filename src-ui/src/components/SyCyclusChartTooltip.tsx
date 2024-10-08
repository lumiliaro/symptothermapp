import { Button, Paper, Text } from "@mantine/core";
import dayjs from "dayjs";
import { TooltipProps } from "recharts";
import { DATETIME_FORMAT_UI } from "../utils/DateFormats.utils";

export default function SyCyclusChartTooltip(
    props: TooltipProps<number, string> & {
        isOpen: boolean;
        setIsOpen: (isOpen: boolean) => void;
        label?: string;
    }
) {
    const { payload, label, isOpen, setIsOpen } = props;

    if (!isOpen) {
        return <></>;
    }
    return (
        <Paper px="md" py="sm" withBorder shadow="md" radius="md">
            <Button onClick={() => setIsOpen(false)}>Schließen</Button>
            <Text fw={500} mb={5}>
                Datum: {label}
            </Text>
            {payload?.map((item) => (
                <div key={item.payload.date}>
                    <Text fz="sm">Zyklustag: {item.payload.cyclusDay}</Text>
                    <Text fz="sm">
                        Temperatur: {item.payload.temperature} {" °C"}
                    </Text>
                    {item.payload.cervicalMucus && (
                        <Text fz="sm">
                            Zervixschleim: {item.payload.cervicalMucus}
                        </Text>
                    )}
                    {item.payload.bleeding && (
                        <Text c="red.6" fz="sm">
                            Blutung: {item.payload.bleeding}
                        </Text>
                    )}

                    {item.payload.cyclusDotType === "FERTILE" && (
                        <Text c="red.6" fz="sm" fw={700}>
                            Mögliche Fruchtbarkeit!
                        </Text>
                    )}

                    {item.payload.cyclusDotType === "INFERTILE" && (
                        <Text c="green.6" fz="sm" fw={700}>
                            Mögliche Unfruchtbarkeit!
                        </Text>
                    )}

                    <Text fz="sm">
                        {"Messung am: "}
                        {dayjs(item.payload.createdAt).format(
                            DATETIME_FORMAT_UI
                        )}
                        {" Uhr"}
                    </Text>
                    <Text fz="sm">
                        {"Aktualisiert am: "}
                        {dayjs(item.payload.updatedAt).format(
                            DATETIME_FORMAT_UI
                        )}
                        {" Uhr"}
                    </Text>
                </div>
            ))}
        </Paper>
    );
}
