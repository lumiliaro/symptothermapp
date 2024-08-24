import { SelectProps } from "@mantine/core";
import { useGetCervixTextureOptionsQuery } from "../store/api/generatedApi";
import SySelect from "./SySelect";

export default function SyCervixTextureSelect(props: SelectProps) {
    const { data } = useGetCervixTextureOptionsQuery();

    return (
        <SySelect name="cervixTexture" label="Textur" data={data} {...props} />
    );
}
