import { FormProvider, useForm } from "react-hook-form";
import SyCyclusStatisticSelect from "../../../components/SyCyclusStatisticSelect";
import SyLineChart from "../../../components/SyLineChart";

export default function CyclusView() {
    const form = useForm({
        defaultValues: {
            cyclus: null,
        },
        mode: "onChange",
    });

    return (
        <FormProvider {...form}>
            <form>
                <SyCyclusStatisticSelect />
                <SyLineChart />
            </form>
        </FormProvider>
    );
}
