import { Button, List, Paper, Text } from "@mantine/core";
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
    const { payload, isOpen, setIsOpen } = props;

    if (!isOpen) {
        return <></>;
    }

    return (
        <Paper px="md" py="sm" withBorder shadow="md" radius="md">
            <Button onClick={() => setIsOpen(false)}>Schließen</Button>
            {payload?.map((item) => (
                <div key={item.payload.date}>
                    <Text fw={500} mb={5}>
                        Datum: {item.payload.date}
                    </Text>

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

                    {item.payload.hadSex && (
                        <Text
                            c={
                                item.payload.withContraceptives
                                    ? "green.6"
                                    : "red.6"
                            }
                            fz="sm"
                        >
                            Sex gehabt{" "}
                            {item.payload.withContraceptives
                                ? "mit Verhütung"
                                : "ohne Verhütung"}
                        </Text>
                    )}

                    {item.payload.cervixOpeningState && (
                        <Text fz="sm">
                            Muttermund-Öffnung:{" "}
                            {item.payload.cervixOpeningState}
                        </Text>
                    )}

                    {item.payload.cervixHeightPosition && (
                        <Text fz="sm">
                            Muttermund-Position:{" "}
                            {item.payload.cervixHeightPosition}
                        </Text>
                    )}

                    {item.payload.cervixTexture && (
                        <Text fz="sm">
                            Muttermund-Textur: {item.payload.cervixTexture}
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

                    {item.payload.disturbances && (
                        <>
                            <Text fz="sm">{"Störungen: "}</Text>
                            <List size="sm" withPadding>
                                {item.payload?.disturbances?.map(
                                    (disturbance: string) => (
                                        <List.Item key={disturbance}>
                                            {disturbance}
                                        </List.Item>
                                    )
                                )}
                            </List>
                        </>
                    )}

                    {item.payload.otherDisturbanceNotes && (
                        <Text fz="sm">
                            {"Sonstige Störungsnotiz: "}
                            <i>{item.payload.otherDisturbanceNotes}</i>
                        </Text>
                    )}

                    {item.payload.notes && (
                        <Text fz="sm">
                            {"Notizen: "}
                            {item.payload.notes}
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
